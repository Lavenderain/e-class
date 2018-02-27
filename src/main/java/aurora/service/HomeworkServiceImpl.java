package aurora.service;

import aurora.model.entity.Homework;
import aurora.service.base.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeworkServiceImpl extends BaseServiceImpl<Homework> implements HomeworkServiceI{
    @Override
    public Homework getHomeworkByuserIdAndHnId(String userId, String hnId) {
        String hql = "FROM Homework h WHERE h.user.id = :userid AND h.hn.id = :hnid";
        Map<String , Object> params = new HashMap<String,Object>();
        params.put("userid",userId);
        params.put("hnid",hnId);
        return getByHql(hql,params);
    }

    @Override
    public List<Homework> findHomeworkByHnId(String hnId, Short state) {
        if(state != null){
            String hql = "FROM Homework h WHERE h.hn.id = :hnId AND h.state = :state";
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("hnId",hnId);
            params.put("state",state);
            return find(hql,params);
        }else{
            String hql = "FROM Homework h WHERE h.hn.id = :hnId AND (h.state = :state1 OR h.state = :state2)";
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("hnId",hnId);
            params.put("state1",(short)1);
            params.put("state2",(short)2);
            return find(hql,params);
        }

    }
}
