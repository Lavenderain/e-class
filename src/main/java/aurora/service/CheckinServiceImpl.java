package aurora.service;

import aurora.model.entity.Checkin;
import aurora.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckinServiceImpl extends BaseServiceImpl<Checkin> implements CheckinServiceI {
    @Override
    public Checkin findUncheckedNumeralCheckin(String classId, String userId) {
        String hql = "FROM Checkin c WHERE  c.user.id = :userId AND c.state = 0 AND c.cn.clazz.id = :classId AND c.cn.type = 0 AND c.cn.state = 1 ";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userId",userId);
        params.put("classId",classId);
        return getByHql(hql,params);
    }

    @Override
    public Long countCheckedSum(String checkinNoticeId) {
        String hql = "SELECT COUNT(*) FROM Checkin c WHERE c.cn.id = :cnId AND c.state = 1";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("cnId",checkinNoticeId);
        return count(hql,params);
    }
}
