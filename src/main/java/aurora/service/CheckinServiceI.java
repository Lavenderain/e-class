package aurora.service;

import aurora.model.entity.Checkin;
import aurora.service.base.BaseServiceI;

public interface CheckinServiceI extends BaseServiceI<Checkin> {

    /**
     * 获取正在进行，未签到的数字考勤
     * @param classId
     * @return
     */
    public Checkin findUncheckedNumeralCheckin(String classId,String userId);

    /**
     * 获得已经签到的学生数目
     * @param checkinNoticeId
     * @return
     */
    Long countCheckedSum(String checkinNoticeId);

}
