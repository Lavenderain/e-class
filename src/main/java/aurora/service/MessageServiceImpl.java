package aurora.service;

import aurora.model.entity.Message;
import aurora.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageServiceI {

    @Override
    public List<Message> findMessageByUser(String currentUserId, String targetUserId) {
        String hql = "FROM Message m WHERE (m.currentuser = :currentuser AND m.targetuser = :targetuser) OR (m.currentuser = :targetuser AND m.targetuser = :currentuser) ORDER BY m.createdatetime ASC";
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("currentuser",currentUserId);
        params.put("targetuser",targetUserId);
        return find(hql,params);
    }
}
