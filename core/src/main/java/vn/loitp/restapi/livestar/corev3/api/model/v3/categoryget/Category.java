
package vn.loitp.restapi.livestar.corev3.api.model.v3.categoryget;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.BaseModel;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbycategory.Backgrounds;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;

public class Category extends BaseModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("background")
    @Expose
    private Object background;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("backgrounds")
    @Expose
    private Backgrounds backgrounds;
    @SerializedName("Rooms")
    @Expose
    private List<Room> rooms = null;

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

    public Object getBackground() {
        return background;
    }

    public void setBackground(Object background) {
        this.background = background;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Backgrounds getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(Backgrounds backgrounds) {
        this.backgrounds = backgrounds;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

}
