package aurora.controller;

import aurora.model.entity.*;
import aurora.model.page.Json;
import aurora.model.page.performancenotice.Model;
import aurora.model.page.performancenotice.PerformanceP;
import aurora.service.ClassServiceI;
import aurora.service.PerformanceNoticeServiceI;
import aurora.service.PerformanceServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class PerformanceController {
    @Autowired
    private ClassServiceI classService;
    @Autowired
    private PerformanceNoticeServiceI pnService;
    @Autowired
    private PerformanceServiceI pService;

    /**
     * 新建表现
     * @param pn
     * @return
     */
    @RequestMapping("tea/createPerformanceNotice")
    @ResponseBody
    public Json createPerformanceNotice(PerformanceNotice pn){
        Clazz c = classService.getById(pn.getClassId());
        pn.setClazz(c);
        pnService.save(pn);
        for(User user : c.getUsers()) {
            if(user.getType() == 0){
                Performance p = new Performance();
                p.setClassId(pn.getClassId());
                p.setPn(pn);
                p.setName(pn.getName());
                p.setUser(user);
                pService.save(p);
            }
        }
        return new Json<String>(true,"新建表现成功",null);
    }

    /**
     * 教师获取班级内所有表现
     * @param classId
     * @return
     */
    @RequestMapping("tea/getPerformance")
    @ResponseBody
    public Json getCheckin(String classId){
        Model model = new Model();
        if(!StringUtils.isEmpty(classId)) {
            List<PerformanceNotice> performances = pnService.findPerformanceNoticeByClassId(classId);
            List<PerformanceP> performancePs = new ArrayList<PerformanceP>(0);
            model.setPerformancenotices(performances);
            Clazz c = classService.getById(classId);
            for (User user : c.getUsers()) {
                if(user.getType() == 0){
                    PerformanceP pp = new PerformanceP();
                    pp.setUsername(user.getName());
                    pp.setSno(user.getSno());
                    for(Performance performance:user.getPerformances()){
                        if(performance.getClassId().equals(classId)){
                            pp.getPerformances().add(performance);
                        }
                    }
                    performancePs.add(pp);
                }
            }
            model.setStuperformances(performancePs);
            return new Json<Model>(true,"获取表现成功",model);

        }{
            return new Json<Model>(false,"请求参数缺失",null);
        }
    }

    /**
     * 删除表现通知
     * @param id
     * @return
     */
    @RequestMapping("tea/deletePerformanceNotice")
    @ResponseBody
    public Json deleteCheckinNotice(String id){
        if(!StringUtils.isBlank(id)){
            PerformanceNotice pn = pnService.getById(id);
            if(pn != null){

                pnService.delete(pn);
            }

            return new Json<String>(true,"表现通知已删除",null);
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }


    }

    /**
     * 变更学生的表现成绩
     * @param id
     * @param score
     * @return
     */
    @RequestMapping("tea/changePerformanceScore")
    @ResponseBody
    public Json changePerformanceScore(String id, Integer score){
        if(!StringUtils.isBlank(id)){
            Performance p = pService.getById(id);
            p.setScore(score);
            pService.update(p);
            return new Json<String>(true,"表现已变更",null);
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }

    }

    /**
     * 重命名表现
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("tea/renamePerformanceNotice")
    @ResponseBody
    public Json renamePerformanceNotice(String id, String name){
        if(!StringUtils.isBlank(id)){
            PerformanceNotice pn = pnService.getById(id);
            pn.setName(name);
            pnService.update(pn);
            return new Json<String>(true,"表现已重命名",null);
        }else{
            return new Json<String>(false,"请求参数缺失",null);
        }



    }




}
