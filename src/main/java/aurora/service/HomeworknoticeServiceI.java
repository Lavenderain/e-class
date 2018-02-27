package aurora.service;

import aurora.model.entity.HomeworkNotice;
import aurora.service.base.BaseServiceI;

import java.util.List;

public interface HomeworknoticeServiceI extends BaseServiceI<HomeworkNotice>{
    List<HomeworkNotice> findHomeworknoticeByClassId(String classId);
}
