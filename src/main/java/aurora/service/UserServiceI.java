package aurora.service;

import aurora.model.entity.User;
import aurora.service.base.BaseServiceI;

public interface UserServiceI extends BaseServiceI<User> {
    /**
     * 通过电话获取一个用户
     *
     * @param phone
     *          电话
     * @return
     *          用户
     */
    public User getUserByPhone(String phone);

    /**
     * 通过邮箱获取一个用户
     *
     * @param email
     *          邮箱
     * @return
     *          用户
     */
    public User getUserByEmail(String email);

}
