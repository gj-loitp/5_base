package vn.puresolutions.livestarv3.activity.notification;

import vn.puresolutions.livestar.core.api.model.BaseModel;

/**
 * File created on 8/11/2017.
 *
 * @author anhdv
 */

public class Notification extends BaseModel {
    private String title;
    private String subcontent;
    private String titleContent;
    private String content;
    private String avatar;
    private String date;

    public Notification(String title, String subcontent, String titleContent, String content, String avatar, String date) {
        this.title = title;
        this.subcontent = subcontent;
        this.titleContent = titleContent;
        this.content = content;
        this.avatar = avatar;
        this.date = date;
    }

    public String getSubcontent() {
        return subcontent;
    }

    public void setSubcontent(String subcontent) {
        this.subcontent = subcontent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
