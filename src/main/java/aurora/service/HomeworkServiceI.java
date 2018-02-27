package aurora.service;

import aurora.model.entity.Homework;
import aurora.service.base.BaseServiceI;

import java.util.List;

public interface HomeworkServiceI extends BaseServiceI<Homework> {
    /**
     * 通过用户id和作业通知id获取一份用户作业
     * @param userId 用户id
     * @param hnId 作业通知id
     * @return
     */
    public Homework getHomeworkByuserIdAndHnId(String userId,String hnId);


    /**
     * 通过作业通知id和作业状态获取作业
     * @param hnId 作业通知id
     * @param state 作业状态 state可为空，当state为空时，默认state=1 或 state = 2
     * @return
     */
    public List<Homework> findHomeworkByHnId(String hnId,Short state);
}
