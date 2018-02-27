package aurora.service;

import aurora.model.entity.CheckinNotice;
import aurora.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckinNoticeServiceImpl extends BaseServiceImpl<CheckinNotice> implements CheckinNoticeServiceI {

    @Override
    public List<CheckinNotice> findCheckinNoticeByClassId(String classId) {
        String hql = "FROM CheckinNotice cn WHERE cn.clazz.id = :id ORDER BY cn.createdatetime DESC";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",classId);
        return find(hql,params);
    }

}
