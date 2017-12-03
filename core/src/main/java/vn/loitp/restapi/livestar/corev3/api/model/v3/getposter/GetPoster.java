
package vn.loitp.restapi.livestar.corev3.api.model.v3.getposter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPoster {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("priority")
    @Expose
    private int priority;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("images_path")
    @Expose
    private ImagesPath imagesPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public ImagesPath getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(ImagesPath imagesPath) {
        this.imagesPath = imagesPath;
    }

}
