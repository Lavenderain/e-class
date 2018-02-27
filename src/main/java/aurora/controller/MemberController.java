package aurora.controller;

import aurora.model.entity.*;
import aurora.model.page.Json;
import aurora.model.page.SessionInfo;
import aurora.model.page.member.Model;
import aurora.service.ClassServiceI;
import aurora.service.GroupServiceI;
import aurora.service.MessageServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MemberController {

    @Autowired
    private GroupServiceI gService;

    @Autowired
    private ClassServiceI classService;

    @Autowired
    private MessageServiceI msgService;


    /**
     * 新建一个班级分组
     * @param g
     * @return
     */
    @RequestMapping("tea/createGroup")
    @ResponseBody
    public Json createGroup(Groupe g){
        if(!StringUtils.isEmpty(g.getClassId())){
            Clazz c = classService.getById(g.getClassId());
            g.setClazz(c);
            gService.save(g);
            return new Json<Groupe>(true,"新建分组成功",g);
      }else{
            return new Json<Groupe>(false,"请求参数缺失",null);
        }
    }

    /**
     * 获取班级所有分组
     * @param classId
     * @return
     */
    @RequestMapping("getGroup")
    @ResponseBody
    public Json getGroup(String classId){
        if(!StringUtils.isEmpty(classId)){
            Model model = new Model();
            Clazz c = classService.getById(classId);
            List<Groupe> gs = null;
            gs = gService.findGroupByClassId(classId);
            Groupe teachers = new Groupe();
            Groupe students = new Groupe();

            teachers.setName("教学团队");
            students.setName("全部学生");

            teachers.setId("teachers");
            students.setId("students");

            for(User u : c.getUsers()){
                if(u.getType() == 1){
                    teachers.getUsers().add(u);
                }else if(u.getType() == 0){
                    students.getUsers().add(u);
                }
            }
            model.setGroups(gs);
            model.setSetting(c.getSetting());
            model.setTeachers(teachers);
            model.setStudents(students);

            return new Json<Model>(true,"获取分组成功",model);
        }else{
            return new Json<Model>(false,"请求参数缺失",null);
        }

    }

    /**
     * 打开，关闭自主选组功能
     * @param classId
     * @return
     */
    @RequestMapping("tea/changeGroupSetting")
    @ResponseBody
    public Json changeGroupSetting(String classId){
        if(!StringUtils.isEmpty(classId)){
            String msg = "";
            Clazz c = classService.getById(classId);
            ClassSetting setting = c.getSetting();
            if(setting.getFreejoingroup() == 0){
                setting.setFreejoingroup((short)1);
                classService.updateClassSetting(setting);
                msg = "已允许选组";
            }else{
                setting.setFreejoingroup((short)0);
                classService.updateClassSetting(setting);
                msg = "已停止自主选组";
            }

            return new Json<String>(true,msg,null);
        }else{
            return new Json<Model>(false,"请求参数缺失",null);
        }


    }

    /**
     * 学生加入分组
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("stu/joinGroup")
    @ResponseBody
    public Json joinGroup(String id,HttpSession session){
        if(!StringUtils.isEmpty(id)){
            SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
            gService.addMember(sessionInfo.getCurrentUserId(),id);

            return new Json<String>(true,"加入分组成功",null);
        }else{
            return new Json<Model>(false,"请求参数缺失",null);
        }
    }


    /**
     * 保存一条私信
     * @param msg
     * @param session
     * @return
     */
    @RequestMapping("saveMsg")
    @ResponseBody
    public Json saveMessage(Message msg,HttpSession session){
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        msg.setCurrentuser(sessionInfo.getCurrentUserId());
        msgService.save(msg);
        return new Json<Message>(true,"消息发送成功",msg);
    }

    /**
     * 获取私信
     * @param targetUserId
     * @param session
     * @return
     */
    @RequestMapping("getMsg")
    @ResponseBody
    public Json getMsg(String targetUserId, HttpSession session){
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        List<Message> msgs = null;
        if(!StringUtils.isBlank(targetUserId)){
            msgs = msgService.findMessageByUser(sessionInfo.getCurrentUserId(),targetUserId);
            return new Json<List<Message>>(true,"获取信息成功",msgs);
        }else {
            return new Json<List<Message>>(false,"请求参数缺失",msgs);
        }

    }




}
