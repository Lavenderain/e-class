package aurora.service;

import aurora.model.entity.CheckinNotice;
import aurora.service.base.BaseServiceI;

import java.util.List;

public interface CheckinNoticeServiceI extends BaseServiceI<CheckinNotice> {
    /**
     * 通过班级id获取考勤通知
     * @param classId
     * @return
     */
    public List<CheckinNotice> findCheckinNoticeByClassId(String classId);


}
