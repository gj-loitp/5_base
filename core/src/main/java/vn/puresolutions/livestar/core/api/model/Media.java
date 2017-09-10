package vn.puresolutions.livestar.core.api.model;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 12/9/2015
 */
public class Media extends BaseModel {
    private int id;
    private String link;
    private String thumb;
    private String title;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumb() {
        return thumb != null ? thumb : link;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}