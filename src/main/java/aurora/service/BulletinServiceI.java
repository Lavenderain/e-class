package aurora.service;

import aurora.model.entity.Bulletin;
import aurora.service.base.BaseServiceI;

import java.util.List;

public interface BulletinServiceI extends BaseServiceI<Bulletin> {
    public List<Bulletin> findBulletinByClassId(String classId);
}