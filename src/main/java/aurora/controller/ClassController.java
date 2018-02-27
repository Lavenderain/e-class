package aurora.controller;

import aurora.model.entity.ClassSetting;
import aurora.model.entity.Clazz;
import aurora.model.entity.Folder;
import aurora.model.entity.User;
import aurora.model.page.Json;
import aurora.model.page.SessionInfo;
import aurora.service.ClassServiceI;
import aurora.service.FolderServiceI;
import aurora.utils.RandomCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
@RequestMapping("/")
public class ClassController {

    @Autowired
    private ClassServiceI classService;

    @Autowired
    private FolderServiceI folderService;

    /**
     * 新建班级
     * @param c 参数模型
     * @param session 当前登录用户session
     * @return 状态码和新建的班级class对象
     */
    @RequestMapping("tea/createClass")
    @ResponseBody
    public Json createClass(Clazz c, HttpSession session) {

        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        c.setBuilder(sessionInfo.getUserName());
        c.setInvitecode(RandomCode.getRandemCode1());
        c.getUsers().add(new User(sessionInfo.getCurrentUserId()));
        classService.save(c);
        //为班级创建一个唯一的根文件夹，与班级一对一关系，外键方式关联
        Folder f = new Folder();
        //约定使用"班级根文件夹"作为班级根文件夹的文件夹名称
        f.setName("班级根文件夹");
        f.setClazz(c);
        folderService.save(f);

        //为班级创建一个班级设置

        ClassSetting setting = new ClassSetting();
        setting.setClazz(c);
        classService.saveClassSetting(setting);

        return new Json<Clazz>(true,"班级创建成功",c);
    }

    /**
     * 获取班级
     * @param session 当前登录用户session
     * @return 班级对象集合
     */
    @RequestMapping("getClass")
    @ResponseBody
    public Json getClass(HttpSession session) {
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        Set<Clazz> classes = null;

        classes = classService.getClazzByUserId(sessionInfo.getCurrentUserId());
        if(classes != null){
            return new Json<Set<Clazz>>(true,"获取班级成功",classes);
        }else{
            return new Json<Set<Clazz>>(false,"获取班级失败",classes);
        }
    }

    /**
     * 删除一个班级
     * @param c 参数模型，id不可为空
     * @return stateCode
     */
    @RequestMapping("tea/deleteClass")
    @ResponseBody
    public Json deleteClass(Clazz c){
        Clazz savedClazz = classService.getById(c.getId());
        classService.delete(savedClazz);
        return new Json<String>(true,"班级已删除",null);
    }

    /**
     * 加入班级
     * @param c 参数模型
     * @param session 当前登录用户session
     * @return
     */
    @RequestMapping("joinClass")
    @ResponseBody
    public Json joinClass(Clazz c, HttpSession session){
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        Clazz savedClazz = classService.getClazzByInviteCode(c.getInvitecode());
        if(savedClazz == null){
            return new Json<Clazz>(false,"邀请码错误",null);
        }else{
            Set<User> classUsers = savedClazz.getUsers();
            for(User temp: classUsers){
                if(temp.getId().equals(sessionInfo.getCurrentUserId()))
                    return new Json<String>(false,"您已经加入该班级",null);
            }
            classService.addUser(sessionInfo.getCurrentUserId(),savedClazz.getId());
            return new Json<Clazz>(true,"加入班级成功",savedClazz);
        }
    }

    /**
     * 重命名班级
     * @param c 参数模型
     * @return
     */
    @RequestMapping("tea/editClass")
    @ResponseBody
    public Json editClass(Clazz c){
        Clazz savedClass = classService.getById(c.getId());
        savedClass.setName(c.getName());
        classService.update(savedClass);
        return new Json<Clazz>(true,"已重命名",null);
    }

    /**
     * 退出班级
     * @param c 参数模型
     * @return
     */
    @RequestMapping("stu/outClass")
    @ResponseBody
    public Json outClass(Clazz c, HttpSession session){
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        classService.removeUser(sessionInfo.getCurrentUserId(),c.getId());
        return new Json<String>(true,"已退出班级",null);
    }

    /**
     * 重置班级邀请码
     * @param id
     * @return
     */
    @RequestMapping("tea/resetInviteCode")
    @ResponseBody
    public Json resetInviteCode(String id){
        if(!StringUtils.isEmpty(id)){
            Clazz c = classService.getById(id);
            c.setInvitecode(RandomCode.getRandemCode1());
            classService.update(c);
            return new Json<Clazz>(true,"班级邀请码已重置",c);
        }else{
            return new Json<Clazz>(false,"请求参数缺失",null);
        }

    }

}
