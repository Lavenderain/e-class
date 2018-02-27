package aurora.service;

import aurora.model.entity.Bulletin;
import aurora.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BulletinServiceImpl extends BaseServiceImpl<Bulletin> implements BulletinServiceI {
    @Override
    public List<Bulletin> findBulletinByClassId(String classId) {
        String hql = "FROM Bulletin b WHERE b.clazz.id = :clazzId ORDER BY b.createdatetime ASC";
        Map<String,Object> params = new HashMap<String,Object>(0);
        params.put("clazzId",classId);
        return find(hql,params);
    }
}
