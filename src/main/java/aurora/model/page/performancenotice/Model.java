package aurora.model.page.performancenotice;

import aurora.model.entity.PerformanceNotice;

import java.util.ArrayList;
import java.util.List;

public class Model implements java.io.Serializable {
    private List<PerformanceNotice> performancenotices = new ArrayList<PerformanceNotice>();
    private List<PerformanceP> stuperformances = new ArrayList<PerformanceP>();

    public Model(){}

    public List<PerformanceNotice> getPerformancenotices() {
        return performancenotices;
    }

    public void setPerformancenotices(List<PerformanceNotice> performancenotices) {
        this.performancenotices = performancenotices;
    }

    public List<PerformanceP> getStuperformances() {
        return stuperformances;
    }

    public void setStuperformances(List<PerformanceP> stuperformances) {
        this.stuperformances = stuperformances;
    }
}
