package aurora.model.page.performancenotice;

import aurora.model.entity.Checkin;
import aurora.model.entity.Performance;

import java.util.ArrayList;
import java.util.List;

public class PerformanceP implements java.io.Serializable{
    private String username;
    private String sno;
    private List<Performance> performances = new ArrayList<Performance>();
    private Integer allscores = 0;


    public PerformanceP(){}

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

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public Integer getAllscores() {
        return allscores;
    }

    public void setAllscores(Integer allscores) {
        this.allscores = allscores;
    }
}
