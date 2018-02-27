package aurora.service;

import aurora.dao.BaseDaoI;
import aurora.model.entity.ClassSetting;
import aurora.model.entity.Clazz;
import aurora.model.entity.User;
import aurora.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ClassServiceImpl extends BaseServiceImpl<Clazz> implements ClassServiceI {
    @Autowired
    private BaseDaoI<User> userDao;

    @Autowired
    private BaseDaoI<ClassSetting> settingDao;

    @Override
    public Set<Clazz> getClazzByUserId(String userId) {
        User user = userDao.getById(User.class,userId);
        return user.getClazzs();
    }

    @Override
    public Clazz getClazzByInviteCode(String inviteCode) {
        Clazz c = null;
        String hql = "FROM Clazz c WHERE c.invitecode = :invitecode";
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("invitecode",inviteCode);
        c = getByHql(hql,params);
        return c;
    }

    @Override
    public void addUser(String userId, String classId) {
        User savedUser = userDao.getById(User.class,userId);
        Clazz savedClazz = getById(classId);
        savedClazz.getUsers().add(savedUser);
        savedClazz.setMembersum(savedClazz.getMembersum()+1);

    }

    @Override
    public void removeUser(String userId, String classId) {
        Clazz savedClass = getById(classId);
        User savedUser = userDao.getById(User.class,userId);
        savedClass.getUsers().remove(savedUser);
        savedClass.setMembersum(savedClass.getMembersum()-1);
    }

    @Override
    public void saveClassSetting(ClassSetting setting) {

        settingDao.save(setting);
    }

    @Override
    public void updateClassSetting(ClassSetting setting) {
        settingDao.update(setting);
    }
}
