package aurora.service;

import aurora.model.entity.HomeworkNotice;
import aurora.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeworknoticeServiceImpl extends BaseServiceImpl<HomeworkNotice> implements HomeworknoticeServiceI{
    @Override
    public List<HomeworkNotice> findHomeworknoticeByClassId(String classId) {
        String hql = "FROM HomeworkNotice hn WHERE hn.clazz.id = :classId ORDER BY hn.createdatetime DESC";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("classId",classId);



        return find(hql,params);
    }
}
