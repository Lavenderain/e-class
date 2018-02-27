package aurora.service;

import aurora.model.entity.Bulletin;
import aurora.model.entity.ClassSetting;
import aurora.model.entity.Clazz;
import aurora.service.base.BaseServiceI;

import java.util.Set;

public interface ClassServiceI extends BaseServiceI<Clazz>{
    /**
     * 获得该用户加入的所有班级
     * @param userId
     * @return
     */
    public Set<Clazz> getClazzByUserId(String userId);

    /**
     * 通过班级验证码获取一个班级
     * @param inviteCode
     * @return
     */
    public Clazz getClazzByInviteCode(String inviteCode);

    /**
     * 添加一个用户到班级
     * @param userId 用户id
     * @param classId 班级id
     */
    public void addUser(String userId,String classId);

    /**
     * 从班级中移除一个用户
     * @param userId 用户id
     * @param classId 班级id
     */
    public void removeUser(String userId,String classId);

    /**
     * 保存班级设置
     * @param setting
     */
    public void saveClassSetting(ClassSetting setting);

    /**
     * 更新班级设置
     * @param setting
     */
    public void updateClassSetting(ClassSetting setting);


}
