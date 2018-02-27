package aurora.controller;

import aurora.model.entity.*;
import aurora.model.page.Json;
import aurora.model.page.SessionInfo;
import aurora.model.page.checkinnotice.CheckinP;
import aurora.model.page.checkinnotice.Model;
import aurora.service.CheckinNoticeServiceI;
import aurora.service.CheckinServiceI;
import aurora.service.ClassServiceI;
import aurora.utils.RandomCode;
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
public class CheckinController {
    @Autowired
    private ClassServiceI classService;

    @Autowired
    private CheckinNoticeServiceI cnService;

    @Autowired
    private CheckinServiceI cService;

    /**
     * 新建考勤
     * @param cn
     * @return
     */
    @RequestMapping("tea/createCheckinNotice")
    @ResponseBody
    public Json createCheckinNotice(CheckinNotice cn){
        int sum = 0;
        Clazz c = classService.getById(cn.getClassId());
        cn.setClazz(c);
        cn.setState((short)1);//1代表数字考勤考勤考勤中，停止考勤可置为0
        cn.setCheckincode(RandomCode.getRandemCode2());
        cnService.save(cn);
        for(User user : c.getUsers()) {
            if(user.getType() == 0){
                Checkin checkin = new Checkin();
                checkin.setClassId(cn.getClassId());
                checkin.setCn(cn);
                checkin.setName(cn.getName());
                if(cn.getType()==1){
                    checkin.setState((short)1);//1代表已签到
                }
                checkin.setUser(user);
                cService.save(checkin);
                sum++;
            }

        }
        cn.setSum(sum);
        return new Json<CheckinNotice>(true,"新建考勤成功",cn);
    }

    /**
     * 教师获取班级考勤情况
     * @param classId 班级id
     * @return
     */
    @RequestMapping("tea/getCheckin")
    @ResponseBody
    public Json getCheckin(String classId){
        Model model = new Model();

        if(!StringUtils.isEmpty(classId)) {
            List<CheckinNotice> cns = cnService.findCheckinNoticeByClassId(classId);
            List<CheckinP> checkinPs = new ArrayList(0);
            model.setCheckinnotices(cns);
            Clazz c = classService.getById(classId);
            for (User user : c.getUsers()) {
                if(user.getType() == 0){
                    CheckinP cp = new CheckinP();
                    cp.setUsername(user.getName());
                    cp.setSno(user.getSno());
                    for(Checkin checkin:user.getCheckins()){
                        if(checkin.getClassId().equals(classId))
                        cp.getCheckins().add(checkin);
                    }
                    checkinPs.add(cp);
                }
            }
            model.setStucheckins(checkinPs);
            return new Json<Model>(true,"获取考勤成功",model);
        }else{
            return new Json<Model>(false,"请求参数缺失",null);
        }
        }

    /**
     * 变更学生的考勤状态
     * @param id
     * @param state
     * @return
     */
    @RequestMapping("tea/changeCheckinState")
    @ResponseBody
    public Json changeCheckinState(String id, short state){
        if(!StringUtils.isBlank(id)){
            Checkin c = cService.getById(id);
            if(c != null){
                c.setState(state);
                cService.update(c);
            }
            return new Json<String>(true,"考勤已变更",null);
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }

    }

    /**
     * 重命名考勤
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("tea/renameCheckinNotice")
    @ResponseBody
    public Json renameCheckinNotice(String id, String name){
        if(!StringUtils.isBlank(id)){
            CheckinNotice cn = cnService.getById(id);
            cn.setName(name);
            cnService.update(cn);
            return new Json<String>(true,"已重命名",null);
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }
    }

    /**
     * 删除考勤通知
     * @param id
     * @return
     */
    @RequestMapping("tea/deleteCheckinNotice")
    @ResponseBody
    public Json deleteCheckinNotice(String id){
        if(!StringUtils.isBlank(id)){
            CheckinNotice cn = cnService.getById(id);
            if(cn != null){
                cnService.delete(cn);
            }
            return new Json<String>(true,"考勤通知已删除",null);
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }
    }

    /**
     * 停止考勤
     * @param id 考勤通知id
     * @return
     */
    @RequestMapping("tea/stopCheckin")
    @ResponseBody
    public Json stopCheckin(String id){
        if(!StringUtils.isBlank(id)){
            CheckinNotice cn = cnService.getById(id);
            if(cn != null){
                cn.setState((short)0);
                cnService.update(cn);
            }
            return new Json<String>(true,"考勤已结束",null);
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }
    }

    /**
     * 教师即时获得班级的考勤状态
     * @param id
     * @return
     */
    @RequestMapping("tea/getCurrentCheckinState")
    @ResponseBody
    public Json getCurrentCheckinState(String id){
        if(!StringUtils.isBlank(id)){
            long checkedSum = 0;
            checkedSum = cService.countCheckedSum(id);
            return new Json<Long>(true,"统计成功",checkedSum);
        }else{
            return new Json<Long>(false,"请求参数缺失",null);
        }
    }




    /**
     * 学生检查该班级有没有正在考勤的数字考勤
     * @param classId
     * @return
     */
    @RequestMapping("stu/getUncheckedCheckin")
    @ResponseBody
    public Json checkCheckin(String classId,HttpSession session){
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        if(!StringUtils.isBlank(classId)){
            Checkin c = null;
            c = cService.findUncheckedNumeralCheckin(classId,sessionInfo.getCurrentUserId());
            if( c != null){
                return new Json<String>(true,"考勤进行中",c.getId());
            }else{
                return new Json<String>(false,"没有正在进行的考勤",null);
            }

        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }

    }

    /**
     * 学生签到
     * @param id
     * @param checkinCode
     * @return
     */
    @RequestMapping("stu/checkingin")
    @ResponseBody
    public Json checkingin(String id,String checkinCode){
        if(!StringUtils.isBlank(id)){
            Checkin c = cService.getById(id);
            if(c.getCn().getCheckincode().equals(checkinCode)){
                c.setState((short)1);
                cService.update(c);
                return new Json<String>(true,"已经签到",null);
            }else{
                return new Json<String>(false,"考勤码错误",null);
            }
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }
    }

}
