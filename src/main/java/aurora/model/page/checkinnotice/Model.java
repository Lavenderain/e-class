package aurora.model.page.checkinnotice;

import aurora.model.entity.CheckinNotice;

import java.util.ArrayList;
import java.util.List;

public class Model implements java.io.Serializable {
    private List<CheckinNotice> checkinnotices = new ArrayList<CheckinNotice>();
    private List<CheckinP> stucheckins = new ArrayList<CheckinP>();

    public Model(){}

    public List<CheckinNotice> getCheckinnotices() {
        return checkinnotices;
    }

    public void setCheckinnotices(List<CheckinNotice> checkinnotices) {
        this.checkinnotices = checkinnotices;
    }

    public List<CheckinP> getStucheckins() {
        return stucheckins;
    }

    public void setStucheckins(List<CheckinP> stucheckins) {
        this.stucheckins = stucheckins;
    }
}
