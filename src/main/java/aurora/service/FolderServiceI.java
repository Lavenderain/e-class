package aurora.service;

import aurora.model.entity.Folder;
import aurora.service.base.BaseServiceI;

public interface FolderServiceI extends BaseServiceI<Folder> {
    /**
     * 通过文件夹名称获取一个文件夹，该方法只适用于获取一个班级的根文件夹
     * @param classId
     * @return
     */
    public Folder getFolderByClassId(String classId);
}
