package aurora.service;

import aurora.model.entity.User;
import aurora.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserServiceI {


    @Override
    public User getUserByPhone(String phone) {
        String hql = "FROM User u WHERE u.phone = :phone";
        Map<String,Object> params = new HashMap<String, Object>();
        User user = null;
        params.put("phone",phone);
        user = getByHql(hql,params);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        String hql = "FROM User u WHERE u.email = :email";
        Map<String,Object> params = new HashMap<String, Object>();
        User user = null;
        params.put("email",email);
        user = getByHql(hql,params);
        return user;
    }
}
