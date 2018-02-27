package aurora.service;

import aurora.dao.BaseDaoI;
import aurora.model.entity.Groupe;
import aurora.model.entity.User;
import aurora.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl extends BaseServiceImpl<Groupe> implements GroupServiceI{
    @Autowired
    private BaseDaoI<User> userDao;

    @Override
    public List<Groupe> findGroupByClassId(String classId) {
        String hql = "FROM Groupe g WHERE g.clazz.id = :classId ORDER BY g.createdatetime DESC";
        Map<String,Object> params = new HashMap<String,Object>(0);
        params.put("classId",classId);
        return find(hql,params);
    }

    @Override
    public void addMember(String userId, String groupId) {
        Groupe g = getById(groupId);
        User u = userDao.getById(User.class,userId);
        g.getUsers().add(u);
    }
}
