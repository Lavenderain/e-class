package aurora.model.page;

import java.util.Date;

public class HomeworkNoticeStu implements java.io.Serializable {
    private String id;
    private String title;
    private String content;
    private Short type = 0;
    private Short state = 0;
    private Integer fullscore;
    private Date cutoffdatetime;
    private Date createdatetime;

    private Short homeworkstate = 0;

    public HomeworkNoticeStu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Integer getFullscore() {
        return fullscore;
    }

    public void setFullscore(Integer fullscore) {
        this.fullscore = fullscore;
    }

    public Date getCutoffdatetime() {
        return cutoffdatetime;
    }

    public void setCutoffdatetime(Date cutoffdatetime) {
        this.cutoffdatetime = cutoffdatetime;
    }

    public Date getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }

    public Short getHomeworkstate() {
        return homeworkstate;
    }

    public void setHomeworkstate(Short homeworkstate) {
        this.homeworkstate = homeworkstate;
    }
}
