package vn.puresolutions.livestar.core.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Mail extends BaseModel {
    private long id;
    private String from;
    private String title;
    private String content;
    @SerializedName("created_at")
    private Date createdDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
