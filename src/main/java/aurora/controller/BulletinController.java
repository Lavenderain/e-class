package aurora.controller;

import aurora.model.entity.Bulletin;
import aurora.model.entity.Clazz;
import aurora.model.page.Json;
import aurora.model.page.SessionInfo;
import aurora.service.BulletinServiceI;
import aurora.service.ClassServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class BulletinController {
    @Autowired
    private ClassServiceI classService;
    @Autowired
    private BulletinServiceI bulletinService;

    /**
     * 新建公告
     * @param b
     * @param session
     * @return
     */
    @RequestMapping("tea/createBulletin")
    @ResponseBody
    public Json createBulletin(Bulletin b, HttpSession session){
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        if(!StringUtils.isEmpty(b.getClassId())){
            Clazz c = classService.getById(b.getClassId());
            b.setClazz(c);
            b.setAuthor(sessionInfo.getUserName());
            bulletinService.save(b);
            return new Json<Bulletin>(true,"新建公告成功",b);
        }else{

            return new Json<Bulletin>(false,"请求参数缺失",null);
        }

    }

    /**
     * 获取班级公告
     * @param classId
     * @return
     */
    @RequestMapping("getBulletins")
    @ResponseBody
    public Json getBulletin(String classId){
        List<Bulletin> bs = null;

        bs = bulletinService.findBulletinByClassId(classId);


        return new Json<List<Bulletin>>(true,"获取公告成功",bs);
    }

    /**
     * 更新一个公告
     * @param b
     * @return
     */
    @RequestMapping("tea/updateBulletin")
    @ResponseBody
    public Json updateBulletin(Bulletin b){
        Bulletin savedBulletin = bulletinService.getById(b.getId());
        savedBulletin.setTitle(b.getTitle());
        savedBulletin.setContent(b.getContent());
        bulletinService.update(savedBulletin);
        return new Json<Bulletin>(true,"已保存",null);
    }

    /**
     * 删除一条公告
     * @param id
     * @return
     */
    @RequestMapping("tea/deleteBulletin")
    @ResponseBody
    public Json deleteBulletin(String id){
        if(!StringUtils.isBlank(id)){
            Bulletin b = bulletinService.getById(id);
            bulletinService.delete(b);
            return new Json<Bulletin>(true,"已删除",null);
        }else{
            return new Json<Bulletin>(false,"请求参数缺失",null);
        }


    }

}
