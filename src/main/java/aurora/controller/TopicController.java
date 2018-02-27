package aurora.controller;

import aurora.model.entity.Clazz;
import aurora.model.entity.Comment;
import aurora.model.entity.TopicNotice;
import aurora.model.entity.User;
import aurora.model.page.Json;
import aurora.model.page.SessionInfo;
import aurora.service.ClassServiceI;
import aurora.service.CommentServiceI;
import aurora.service.TopicNoticeServiceI;
import aurora.service.UserServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class TopicController {

    @Autowired
    private ClassServiceI classService;

    @Autowired
    private TopicNoticeServiceI topnService;

    @Autowired
    private CommentServiceI commentService;

    @Autowired
    private UserServiceI userService;

    /**
     * 新建一个班级话题
     * @param topn
     * @param session
     * @return
     */
    @RequestMapping("createTopicnotice")
    @ResponseBody
    public Json saveTopicNotice(TopicNotice topn, HttpSession session){
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
        Clazz c = classService.getById(topn.getClassId());
        topn.setAuthor(sessionInfo.getUserName());
        topn.setClazz(c);
        topnService.save(topn);

        return new Json<TopicNotice>(true,"新建话题成功",topn);
    }

    /**
     * 获取话题通知
     * @param classId
     * @return
     */
    @RequestMapping("getTopicnotice")
    @ResponseBody
    public Json getTopicNotice(String classId){

        List<TopicNotice> topns = topnService.findTopicNoticeByClassId(classId);

        return new Json<List<TopicNotice>>(true,"获取班级话题成功",topns);

    }

    /**
     * 更新一条话题通知
     * @param topn
     * @return
     */
    @RequestMapping("tea/updateTopicnotice")
    @ResponseBody
    public Json updateTopicNotice(TopicNotice topn){
        TopicNotice savedTopn = topnService.getById(topn.getId());
        if(savedTopn != null) {
            savedTopn.setName(topn.getName());
            savedTopn.setContent(topn.getContent());
            topnService.update(savedTopn);
        }
        return new Json<TopicNotice>(true,"编辑成功",null);
    }

    /**
     * 删除一条话题
     * @param id
     * @return
     */
    @RequestMapping("tea/deleteTopicNotice")
    @ResponseBody
    public Json deleteTopicNotice(String id){
        if(!StringUtils.isBlank(id)){
            TopicNotice topn = topnService.getById(id);
            if(topn != null){
                topnService.delete(topn);
            }
        }
        return new Json<TopicNotice>(true,"已删除",null);
    }

    /**
     * 获取话题的评论详情
     * @param topicId
     * @return
     */
    @RequestMapping("getTopicComment")
    @ResponseBody
    public Json getTopicComment(String topicId){
        if(!StringUtils.isBlank(topicId)){
            Map<String,Object> result = new HashMap<String,Object>(0);
            TopicNotice tn = topnService.getById(topicId);
            result.put("topicnotice",tn);
            result.put("comments",tn.getComments());
            return new Json<Map<String,Object>>(true,"获取话题详情成功",result);
        }else {

            return new Json<Map<String,Object>>(false,"请求参数缺失",null);
        }
    }

    /**
     * 评论话题
     * @param comment
     * @param session
     * @return
     */
    @RequestMapping("commentTopic")
    @ResponseBody
    public Json commentTopic (Comment comment,HttpSession session){
        SessionInfo sessionInfo = (SessionInfo)session.getAttribute("sessionInfo");
        if(!StringUtils.isBlank(comment.getTopicId())){
            User u = userService.getById(sessionInfo.getCurrentUserId());
            TopicNotice tn = topnService.getById(comment.getTopicId());
            comment.setTopicnotice(tn);
            comment.setUsername(u.getName());
            comment.setUsericon(u.getIcon());
            commentService.save(comment);
            tn.setCommentsum(tn.getCommentsum()+1);
            topnService.update(tn);

           return new Json<Comment>(true,"评论成功",comment);
        }else {
            return new Json<Comment>(false,"请求参数缺失",null);
        }


    }

    /**
     * 删除一条话题评论
     * @param id
     * @return
     */
    @RequestMapping("deleteComment")
    @ResponseBody
    public Json deleteComment(String id){
        if(!StringUtils.isBlank(id)){
            Comment c = commentService.getById(id);
            if(c != null){
                commentService.delete(c);
            }
            return new Json<String>(true,"评论已删除",null);
        }else {
            return new Json<String>(false,"请求参数缺失",null);
        }


    }




}
