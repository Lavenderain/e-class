package aurora.service;

import aurora.model.entity.Folder;
import aurora.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FolderServiceImpl extends BaseServiceImpl<Folder> implements FolderServiceI {
    @Override
    public Folder getFolderByClassId(String classId) {
        String hql = "FROM Folder f WHERE f.clazz.id = :clazzId";
        Map<String,Object> params = new HashMap<String,Object>(0);
        params.put("clazzId",classId);
        return getByHql(hql,params);
    }
}

