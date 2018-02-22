package vn.loitp.app.model;

import java.io.Serializable;

/**
 * Created by LENOVO on 2/18/2018.
 */

public class Chap implements Serializable{
    private int id;
    private String title;
    private String content;
    private String linkMp3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getLinkMp3() {
        return linkMp3;
    }

    public void setLinkMp3(String linkMp3) {
        this.linkMp3 = linkMp3;
    }
}
