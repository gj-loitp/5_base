
package vn.loitp.livestar.corev3.api.model.v3.categoryget;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.livestar.corev3.api.model.v3.roomgetbycategory.Backgrounds;

public class CategoryGet {

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
    @SerializedName("backgrounds")
    @Expose
    private Backgrounds backgrounds;

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

    public Backgrounds getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(Backgrounds backgrounds) {
        this.backgrounds = backgrounds;
    }

}
