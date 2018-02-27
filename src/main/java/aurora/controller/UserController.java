package aurora.controller;

import aurora.model.entity.User;
import aurora.model.page.Json;
import aurora.model.page.SessionInfo;
import aurora.service.UserServiceI;
import aurora.utils.MD5Util;
import aurora.utils.RandomGraphic;
import aurora.utils.VerifyCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;


@Controller
@RequestMapping("/")
public class UserController extends BaseController{

    @Autowired
    private UserServiceI userService;

    /**
     * 用户注册
     *
     * @param user
     *      参数模型
     * @return
     *      stateCode
     */
    @RequestMapping("reg")
    @ResponseBody
    public Json register(User user,HttpSession session) {
        String currentVerifyCode = null;
        currentVerifyCode = (String)session.getAttribute("verifyCode");
        //先验证用户的验证码是否有效，防止机器人登录
        if(!StringUtils.isBlank(user.getVerifycode()) && currentVerifyCode != null && currentVerifyCode.equalsIgnoreCase(user.getVerifycode())){
            //验证该用户的电话号码或邮箱是否已经注册
            if (!StringUtils.isBlank(user.getPhone())) {
                if(userService.getUserByPhone(user.getPhone()) != null){
                    return new Json<String>(false,"该电话号码已经注册",null);
                }
            }else if(!StringUtils.isBlank(user.getEmail())){
                if(userService.getUserByEmail(user.getEmail()) != null){
                    return new Json<String>(false,"该邮箱已经注册",null);
                }
            }else {
                return new Json<String>(false,"账号不可为空",null);
            }
            //密码摘要
            user.setPwd(MD5Util.md5(user.getPwd()));
            //保存用户
            userService.save(user);
            Json<User> j = new Json<User>(true,"成功了",null);
            return j;

        }else{
            //重置验证码
            session.setAttribute("verifyCode",null);
            return new Json<String>(false,"验证码错误",null);
        }

    }

    /**
     * 验证用户是否注册
     * @param user
     *          参数模型
     * @return
     *          stateCode
     */
    @RequestMapping("checkRegState")
    @ResponseBody
    public Json checkRegState(User user){
        //验证该用户的电话号码或邮箱是否已经注册
        if (!StringUtils.isBlank(user.getPhone())) {
            if(userService.getUserByPhone(user.getPhone()) != null){
                return new Json<String>(false,"电话号码已经注册",null);
            }
        }else if(!StringUtils.isBlank(user.getEmail())){
            if(userService.getUserByEmail(user.getEmail()) != null){
                return new Json<String>(false,"邮箱已经注册",null);
            }
        }
        return new Json<String>(true,"",null);
    }

    /**
     * 用户登录
     * @param user
     *          参数模型
     * @param session
     *          Http session
     * @return
     */
    @RequestMapping("log")
    @ResponseBody
    public Json login(User user, HttpSession session){
        User registeredUser = null;
        String currentVerifyCode = null;
        currentVerifyCode = (String)session.getAttribute("verifyCode");

        //先验证用户的验证码是否有效，防止机器人登录
        if(!StringUtils.isBlank(user.getVerifycode()) && currentVerifyCode != null && currentVerifyCode.equalsIgnoreCase(user.getVerifycode())) {

            if (!StringUtils.isBlank(user.getPhone())) {
                registeredUser = userService.getUserByPhone(user.getPhone());
            }else if(!StringUtils.isBlank(user.getEmail())){
                registeredUser = userService.getUserByEmail(user.getEmail());
            }
            if(registeredUser == null){
                return new Json<Integer>(false,"该用户不存在",-1);
            }else if(registeredUser.getPwd().equals(MD5Util.md5(user.getPwd()))){
                SessionInfo sessionInfo = new SessionInfo(registeredUser.getId());
                sessionInfo.setUserType(registeredUser.getType());
                sessionInfo.setUserName(registeredUser.getName());
                session.setAttribute("sessionInfo",sessionInfo);
                Integer type = 0;
                if(registeredUser.getType() == 1)
                    type = 1;
                return new Json<Integer>(true,"成功了",type);
            }else{
                return new Json<Integer>(false,"用户名或密码错误",-1);
            }

        }else {

            //重置验证码
            session.setAttribute("verifyCode",null);
            return new Json<Integer>(false,"验证码错误",-1);
        }

    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("logout")
    @ResponseBody
    public Json logout(HttpSession session){
        if (session != null) {
            session.invalidate();
        }
        return new Json<String>(true,"已退出登录",null);
    }

    /**
     * 获取当前用户信息
     * @param session
     * @return
     */
    @RequestMapping("getUserInfo")
    @ResponseBody
    public Json getUserInfo(HttpSession session){
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        User currentUser = userService.getById(sessionInfo.getCurrentUserId());
        return new Json<User>(true,"获取到用户信息",currentUser);
    }

    /**
     * 更新用户的基本信息
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("updateUserInfo")
    @ResponseBody
    public Json updateUserInfo(User user,HttpSession session){
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        User savedUser = userService.getById(sessionInfo.getCurrentUserId());
        savedUser.setName(user.getName());
        savedUser.setSchool(user.getSchool());
        savedUser.setSno(user.getSno());
        userService.update(savedUser);
        return new Json<User>(true,"保存信息成功",savedUser);
    }

    /**
     * 解绑邮箱
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("unbindEmail")
    @ResponseBody
    public Json unbindEmail(User user,HttpSession session){
        System.out.println(user.getPwd()+ "ssdsdfdfd");
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        User savedUser = userService.getById(sessionInfo.getCurrentUserId());
        if(savedUser.getPwd().equals(MD5Util.md5(user.getPwd()))){
            savedUser.setEmail("");
            userService.update(savedUser);
            return new Json<User>(true,"邮箱已解绑",savedUser);
        }else{
            return new Json<User>(false,"密码错误",savedUser);
        }
    }

    /**
     * 绑定邮箱
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("bindEmail")
    @ResponseBody
    public Json bindEmail(User user,HttpSession session){
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        User savedUser = userService.getById(sessionInfo.getCurrentUserId());
        if(savedUser.getPwd().equals(MD5Util.md5(user.getPwd()))){
            savedUser.setEmail(user.getEmail());
            userService.update(savedUser);
            return new Json<User>(true,"邮箱已绑定",savedUser);
        }else{
            return new Json<User>(false,"密码错误",savedUser);
        }

    }

    /**
     * 修改密码
     * @param session
     * @param oldpwd
     * @param newpwd
     * @return
     */
    @RequestMapping("updatePwd")
    @ResponseBody
    public Json updatePwd(HttpSession session,String oldpwd,String newpwd) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        User savedUser = userService.getById(sessionInfo.getCurrentUserId());
        if (savedUser.getPwd().equals(MD5Util.md5(oldpwd))) {
            savedUser.setPwd(MD5Util.md5(newpwd));
            userService.update(savedUser);
            return new Json<String>(true, "密码已修改", null);
        } else {
            return new Json<String>(false, "旧密码错误", null);
        }
    }

    /**
     * 获取图片验证码，图片验证码的有效时间为60秒,以后可以加入任务调度
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("getVerifyCode")
    @ResponseBody
    public Json getCaptcha(HttpSession session) throws Exception {
        int w = 200, h = 80;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        VerifyCodeUtils verifyUtil = new VerifyCodeUtils();
        String verifyCode = verifyUtil.generateVerifyCode(4);
        verifyUtil.outputImage(w, h, output, verifyCode);
        session.setAttribute("verifyCode",verifyCode);
        byte[] captcha = output.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String imagestr =  encoder.encode(captcha);// 返回Base64编码过的字节数组字符串
        return new Json<String>(true,"获取验证码成功","data:image/png;base64," + imagestr);
    }

    /**
     * 修该手机
     * @param session
     * @param captcha
     * @param phone
     * @return
     */
    @RequestMapping("updatePhone")
    @ResponseBody
    public Json updatePhone(HttpSession session,String captcha,String phone, String verifyCode){
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        User savedUser = userService.getById(sessionInfo.getCurrentUserId());

        //如果向服务器获取验证的时间超过了60秒，则验证信息无效
        //System.out.println("上次访问时间：" + session.getLastAccessedTime());
        if(System.currentTimeMillis() - session.getLastAccessedTime() > 60000){
            session.setAttribute("verifyCode",null);
            return new Json<String>(false,"验证码已失效，点击重新获取",null);
        }
        String savedVerifyCode = null;
        savedVerifyCode = (String)session.getAttribute("verifyCode");
        System.out.println("验证码：" + savedVerifyCode);
        if(savedVerifyCode != null && savedVerifyCode.equalsIgnoreCase(verifyCode)){
            User user = userService.getById(sessionInfo.getCurrentUserId());
            user.setPhone(phone);
            userService.update(user);
            return new Json<String>(true,"电话已更改",null);
        }else{
            return new Json<String>(false,"验证码错误",null);
        }
    }

    /**
     * 用户修改头像
     * @param base64Img
     * @param session
     * @return
     */
    @RequestMapping("updateUserIcon")
    @ResponseBody
    public Json updateUserIcon(String base64Img, HttpSession session){
        if(!StringUtils.isBlank(base64Img)){
            SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
            User u = userService.getById(sessionInfo.getCurrentUserId());
            u.setIcon(base64Img);
            userService.update(u);
            return new Json<String>(true,"头像修改成功",null);
        }else {
            return new Json<String>(false,"请求参数缺失",null);
        }
    }
}
