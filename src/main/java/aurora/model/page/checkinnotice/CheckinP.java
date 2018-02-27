package aurora.model.page.checkinnotice;

import aurora.model.entity.Checkin;

import java.util.ArrayList;
import java.util.List;

public class CheckinP implements java.io.Serializable{
    private String username;
    private String sno;
    private List<Checkin> checkins = new ArrayList<Checkin>();
    private Integer checked = 0;
    private Integer absent = 0;
    private Integer late = 0;
    private Integer leave = 0;

    public CheckinP(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public List<Checkin> getCheckins() {
        return checkins;
    }

    public void setCheckins(List<Checkin> checkins) {
        this.checkins = checkins;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }
}
