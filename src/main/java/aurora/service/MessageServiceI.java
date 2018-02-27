package aurora.service;

import aurora.model.entity.Message;
import aurora.service.base.BaseServiceI;

import java.util.List;

public interface MessageServiceI extends BaseServiceI<Message> {
    public List<Message> findMessageByUser(String currentUserId, String targetUserId);
}
