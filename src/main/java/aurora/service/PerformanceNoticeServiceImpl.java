package aurora.service;

import aurora.model.entity.PerformanceNotice;
import aurora.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerformanceNoticeServiceImpl extends BaseServiceImpl<PerformanceNotice> implements PerformanceNoticeServiceI{
    @Override
    public List<PerformanceNotice> findPerformanceNoticeByClassId(String classId) {
        String hql = "FROM PerformanceNotice pn WHERE pn.clazz.id = :id ORDER BY pn.createdatetime DESC";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",classId);
        return find(hql,params);
    }
}
