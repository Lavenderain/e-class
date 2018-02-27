package aurora.service;

import aurora.model.entity.PerformanceNotice;
import aurora.service.base.BaseServiceI;

import java.util.List;

public interface PerformanceNoticeServiceI extends BaseServiceI<PerformanceNotice> {
    /**
     * 通过班级id获取班级表现通知
     * @param classId
     * @return
     */
    List<PerformanceNotice> findPerformanceNoticeByClassId(String classId);
}
