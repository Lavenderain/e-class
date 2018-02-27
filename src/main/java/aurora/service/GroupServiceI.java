package aurora.service;

import aurora.model.entity.Groupe;
import aurora.service.base.BaseServiceI;

import java.util.List;

public interface GroupServiceI extends BaseServiceI<Groupe> {
    /**
     * 通过班级id获得分组
     * @param classId
     * @return
     */
    public List<Groupe> findGroupByClassId(String classId);


    /**
     * 添加一个成员到分组
     * @param userId
     * @param groupId
     */
    public void addMember(String userId,String groupId);
}
