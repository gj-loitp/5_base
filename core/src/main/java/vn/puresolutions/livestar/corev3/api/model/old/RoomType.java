package vn.puresolutions.livestar.corev3.api.model.old;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class RoomType extends BaseModel {
    private int id;
    private String title;
    private String slug;
    private String description;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
