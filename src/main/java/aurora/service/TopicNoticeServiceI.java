package aurora.service;

import aurora.model.entity.TopicNotice;
import aurora.service.base.BaseServiceI;

import java.util.List;

public interface TopicNoticeServiceI extends BaseServiceI<TopicNotice> {
    public List<TopicNotice> findTopicNoticeByClassId(String classId);
}
