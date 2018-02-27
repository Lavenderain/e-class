package aurora.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "comment", catalog = "")
public class Comment implements java.io.Serializable {

    private String id;
    private Date createdatetime;
    private String username;
    private String usericon;
    private String content;
    private Integer upvote = 0;
    @JSONField(serialize = false)
    private TopicNotice topicnotice;

    private String topicId;

    public Comment(){}


    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    public String getId() {
        if (!StringUtils.isBlank(this.id)) {
            return this.id;
        }
        return UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDATETIME")
    public Date getCreatedatetime() {
        if (this.createdatetime != null)
            return this.createdatetime;
        return new Date();
    }
    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }


    @Basic
    @Column(name = "USERNAME", length = 32)
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Lob
    @Column(name = "USERICON")
    public String getUsericon() {
        return usericon;
    }
    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    @Basic
    @Column(name = "CONTENT", length = 10000)
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "UPVOTE")
    public Integer getUpvote() {
        return upvote;
    }

    public void setUpvote(Integer upvote) {
        this.upvote = upvote;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TOPICNOTICE", nullable = false)
    public TopicNotice getTopicnotice() {
        return topicnotice;
    }

    public void setTopicnotice(TopicNotice topicnotice) {
        this.topicnotice = topicnotice;
    }

    @Transient
    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
}
