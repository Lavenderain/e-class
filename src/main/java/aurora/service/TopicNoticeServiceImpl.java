package aurora.service;

import aurora.model.entity.TopicNotice;
import aurora.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicNoticeServiceImpl extends BaseServiceImpl<TopicNotice> implements TopicNoticeServiceI{
    @Override
    public List<TopicNotice> findTopicNoticeByClassId(String classId) {
        String hql = "FROM TopicNotice topn WHERE topn.clazz.id = :clazzid ORDER BY topn.createdatetime DESC";
        Map<String,Object> params = new HashMap<String, Object>(0);
        params.put("clazzid",classId);
        return find(hql,params);
    }
}
