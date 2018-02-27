package aurora.controller;

import aurora.model.entity.*;
import aurora.model.page.HomeworkNoticeStu;
import aurora.model.page.Json;
import aurora.model.page.SessionInfo;
import aurora.service.*;
import aurora.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/")
public class HomeworkController {
    @Autowired
    private HomeworknoticeServiceI hnService;

    @Autowired
    private HomeworkServiceI hService;

    @Autowired
    ClassServiceI classService;

    @Autowired
    UserfileServiceI userfileService;

    /**
     * 新建班级作业通知
     *
     * @param hn 作业通知
     * @return
     */
    @RequestMapping("tea/createHomeworknotice")
    @ResponseBody
    public Json saveHomeworkNotice(HomeworkNotice hn) {
        Clazz c = classService.getById(hn.getClassId());
        hn.setClazz(c);
        hnService.save(hn);
        int unsubmit = 0;
        for (User user : c.getUsers()) {
            if (user.getType() == 0) {
                Homework homework = new Homework();
                homework.setName(hn.getTitle());
                homework.setHn(hn);
                homework.setUser(user);
                homework.setUsername(user.getName());
                hService.save(homework);
                unsubmit += 1;
            }
        }
        hn.setUnsubmit(unsubmit);
        hnService.update(hn);
        return new Json<HomeworkNotice>(true, "新建班级作业成功", hn);
    }

    /**
     * 获取作业通知
     *
     * @param classId
     * @return
     */
    @RequestMapping("getHomeworknotices")
    @ResponseBody
    public Json getHomeworknotices(String classId, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        List<HomeworkNotice> hns = null;
        List<HomeworkNoticeStu> hns_stu = new ArrayList<HomeworkNoticeStu>(0);

        hns = hnService.findHomeworknoticeByClassId(classId);

        if (sessionInfo.getUserType() == 0) {
            for (HomeworkNotice hn : hns) {
                HomeworkNoticeStu hn_stu = new HomeworkNoticeStu();
                BeanUtils.copyProperties(hn, hn_stu);

                //每个学生作业的状态
                Homework homework = hService.getHomeworkByuserIdAndHnId(sessionInfo.getCurrentUserId(), hn.getId());
                if (homework != null) {
                    hn_stu.setHomeworkstate(homework.getState());
                }

                hns_stu.add(hn_stu);
            }

            return new Json<List<HomeworkNoticeStu>>(true, "获取班级作业成功", hns_stu);

        } else {

            return new Json<List<HomeworkNotice>>(true, "获取班级作业成功", hns);

        }

    }

    /**
     * 更新作业通知
     *
     * @param hn
     * @return
     */
    @RequestMapping("tea/updateHomeworknotice")
    @ResponseBody
    public Json updateHomeworknotices(HomeworkNotice hn) {
        HomeworkNotice savedHn = hnService.getById(hn.getId());
        savedHn.setTitle(hn.getTitle());
        savedHn.setContent(hn.getContent());
        savedHn.setCutoffdatetime(hn.getCutoffdatetime());
        savedHn.setFullscore(hn.getFullscore());
        hnService.update(savedHn);
        return new Json<List<HomeworkNotice>>(true, "作业通知已更新", null);
    }

    /**
     * 删除一条作业通知
     *
     * @param id
     * @return
     */
    @RequestMapping("tea/deleteHomeworknotice")
    @ResponseBody
    public Json deleteHomeworknotices(String id) {
        HomeworkNotice hn = hnService.getById(id);
        hnService.delete(hn);
        return new Json<List<HomeworkNotice>>(true, "作业通知已删除", null);
    }

    /**
     * 获取一份作业
     *
     * @param hnId
     * @param session
     * @return
     */
    @RequestMapping("stu/getHomework")
    @ResponseBody
    public Json getHomework(String hnId, HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        String userId = sessionInfo.getCurrentUserId();
        Homework homework = hService.getHomeworkByuserIdAndHnId(userId, hnId);
        return new Json<Homework>(true, "获取作业成功", homework);
    }

    /**
     * 保存学生提交的作业文件
     *
     * @param file
     * @param req
     * @return 文件Id
     * @throws IOException
     */
    @RequestMapping("stu/uploadHomeworkFile")
    @ResponseBody
    public Json uploadHomeworkFile(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        //ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{"classpath:spring.xml", "classpath:spring-hibernate.xml"});
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
        SessionFactory fac = (SessionFactory) ctx.getBean("sessionFactory");

        Map<String, String> fileInfo = new HashMap<String, String>();
        String fileId = "";
        String realPath = req.getSession().getServletContext().getRealPath("/");
        String fileOrganization = "userfile" + File.separator + "homework" + File.separator;
        String directoryPath = realPath + fileOrganization;
        File directory = new File(directoryPath);
        if (!directory.exists()) {//如果目录不存在，创建目录
            directory.mkdirs();
        }
        if (!file.isEmpty()) {
            String time = System.currentTimeMillis() + "";
            file.transferTo(new File(directory + File.separator + time + file.getOriginalFilename()));
            fileInfo.put("name", file.getOriginalFilename());
            fileInfo.put("extension", StringUtil.getFileExtension(file.getOriginalFilename()));
            fileInfo.put("type", file.getContentType());
            fileInfo.put("file", directory + File.separator + time + file.getOriginalFilename());

        }
        Session session = fac.openSession();
        Userfile userfile = new Userfile();
        userfile.setFile(fileInfo.get("file"));
        userfile.setIstemp((short) 1);
        userfile.setType(fileInfo.get("type"));
        userfile.setName(fileInfo.get("name"));
        userfile.setExtension(fileInfo.get("extension"));
        session.beginTransaction();
        session.save(userfile);
        session.getTransaction().commit();
        fileId = userfile.getId();
        session.close();
        return new Json<String>(true, "文件已保存", fileId);
    }

    /**
     * 提交作业
     * @param id
     * @param msg
     * @param files
     * @return
     */
    @RequestMapping("stu/submitHomework")
    @ResponseBody
    public Json submitHomework(String id, String msg, String[] files) {
        Homework homework = null;
        if (!StringUtils.isBlank(id) && files != null) {
            homework = hService.getById(id);

            for (String file : files) {
                Userfile userfile = userfileService.getById(file);
                userfile.setIstemp((short) 0);
                userfileService.update(userfile);
                homework.getFiles().add(userfile);
            }

            HomeworkNotice hn = homework.getHn();

            int unsubmit = hn.getUnsubmit();
            int unreviewed = hn.getUnreviewed();
            if(homework.getState() == 0 || homework.getState() == 3){
                hn.setUnreviewed(unreviewed + 1);
                hn.setUnsubmit(unsubmit - 1);
            }

            homework.setState((short) 1);
            homework.setSubmitdatetime(new Date());
            homework.setMsg(msg);

            hService.update(homework);
            hnService.update(hn);
            System.out.println("****************upate*************");

        }
        return new Json<Homework>(true, "作业提交成功", homework);
    }

    /**
     * 移除作业文件（附件）
     * @param hid
     * @param fid
     * @return
     */
    @RequestMapping("stu/removeHomeworkfile")
    @ResponseBody
    public Json removeHomeworkfile(String hid,String fid){
        if(!StringUtils.isBlank(hid) && !StringUtils.isBlank(fid)){
            Userfile userfile = userfileService.getById(fid);
            Homework homework = hService.getById(hid);
            homework.getFiles().remove(userfile);
            userfile.setDeleted((short)1);
            userfileService.update(userfile);
            hService.update(homework);

        }
        return new Json<String>(true,"文件已移除",null);
    }

    /**
     * 通过作业通知id和作业状态获取作业
     * @param id
     * @param state
     * @return
     */
    @RequestMapping("tea/getHomeworks")
    @ResponseBody
    public Json getHomeworks(String id,Short state){
        List<Homework> homeworks;
        homeworks = hService.findHomeworkByHnId(id,state);
        return new Json<List<Homework>>(true,"获取作业成功",homeworks);
    }


    /**
     * 教师保存作业批改
     * @param h
     * @return
     */
    @RequestMapping("tea/saveReview")
    @ResponseBody
    public Json saveReview(Homework h){
        if(!StringUtils.isBlank(h.getId())){
            Homework savedHomework = hService.getById(h.getId());
            savedHomework.setScore(h.getScore());
            HomeworkNotice hn = savedHomework.getHn();
            if(savedHomework.getState() == 1){
                hn.setUnreviewed(hn.getUnreviewed()-1);
                hn.setReviewed(hn.getReviewed()+1);
                hnService.update(hn);
            }
            savedHomework.setState((short)2);
            savedHomework.setComment(h.getComment());
            hService.update(savedHomework);
            return new Json<String>(true,"批改已保存",null);
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }
    }

    /**
     * 教师打回一份作业
     * @param id
     * @return
     */
    @RequestMapping("tea/rejectHomework")
    @ResponseBody
    public Json rejectH(String id){
        if(!StringUtils.isBlank(id)){
            Homework savedHomework = hService.getById(id);
            HomeworkNotice hn = savedHomework.getHn();
            if(savedHomework.getState() == 1 || savedHomework.getState() == 2 ){
                if(savedHomework.getState() == 1){
                    hn.setUnreviewed(hn.getUnreviewed()-1);
                }else if(savedHomework.getState() == 2){
                    hn.setReviewed(hn.getReviewed()-1);
                }
                hn.setUnsubmit(hn.getUnsubmit()+1);
                hnService.update(hn);
            }
            savedHomework.setState((short)3);
            hService.update(savedHomework);
            return new Json<String>(true,"作业已打回",null);
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }
    }

}
