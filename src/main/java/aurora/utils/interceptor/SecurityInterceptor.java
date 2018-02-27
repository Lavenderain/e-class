package aurora.utils.interceptor;

import aurora.model.entity.User;
import aurora.model.page.SessionInfo;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SecurityInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);

    private List<String> excludeUrls;
    private String teaUrlsPrefix;
    private String stuUrlsPrefix;


    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public String getTeaUrlsPrefix() {
        return teaUrlsPrefix;
    }

    public void setTeaUrlsPrefix(String teaUrlsPrefix) {
        this.teaUrlsPrefix = teaUrlsPrefix;
    }

    public String getStuUrlsPrefix() {
        return stuUrlsPrefix;
    }

    public void setStuUrlsPrefix(String stuUrlsPrefix) {
        this.stuUrlsPrefix = stuUrlsPrefix;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        //logger.info(url);
        //System.out.println("进入拦截器的地址（去前缀）: " + url);
        if (excludeUrls.contains(url)) {
            return true;
        } else {
            if (request.getSession().getAttribute("sessionInfo") == null) {
                //System.out.println("用户没有登录，无访问权限！");
                return false;
            } else {
                Short userType = ((SessionInfo) request.getSession().getAttribute("sessionInfo")).getUserType();
                //System.out.println(((SessionInfo) request.getSession().getAttribute("sessionInfo")).getCurrentUserId() + ", 正请求资源,类型为：" + userType);
                if (url.contains(stuUrlsPrefix) || url.contains(teaUrlsPrefix)) {
                    if (url.contains(stuUrlsPrefix) && userType == 0) {
                        return true;
                    } else if (url.contains(teaUrlsPrefix) && userType == 1) {
                        return true;
                    } else {
                        //System.out.println("该用户无权访问该资源！");
                        return false;
                    }
                } else {
                    return true;
                }
            }

        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
