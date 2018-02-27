package aurora.model.page.member;

import aurora.model.entity.ClassSetting;
import aurora.model.entity.Groupe;
import aurora.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Model implements java.io.Serializable {

    private Groupe teachers;
    private Groupe students;
    private ClassSetting setting;
    private List<Groupe> groups = new ArrayList<Groupe>(0);

    public Model(){}

    public Groupe getTeachers() {
        return teachers;
    }

    public void setTeachers(Groupe teachers) {
        this.teachers = teachers;
    }

    public ClassSetting getSetting() {
        return setting;
    }

    public void setSetting(ClassSetting setting) {
        this.setting = setting;
    }

    public List<Groupe> getGroups() {
        return groups;
    }

    public void setGroups(List<Groupe> groups) {
        this.groups = groups;
    }

    public Groupe getStudents() {
        return students;
    }

    public void setStudents(Groupe students) {
        this.students = students;
    }
}
