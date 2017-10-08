
package vn.loitp.flickr.model.photosetgetphotos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photoset {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("primary")
    @Expose
    private String primary;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("ownername")
    @Expose
    private String ownername;
    @SerializedName("photo")
    @Expose
    private List<Photo> photo = null;
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("per_page")
    @Expose
    private double perPage;
    @SerializedName("perpage")
    @Expose
    private double perpage;
    @SerializedName("pages")
    @Expose
    private double pages;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("title")
    @Expose
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public double getPerPage() {
        return perPage;
    }

    public void setPerPage(double perPage) {
        this.perPage = perPage;
    }

    public double getPerpage() {
        return perpage;
    }

    public void setPerpage(double perpage) {
        this.perpage = perpage;
    }

    public double getPages() {
        return pages;
    }

    public void setPages(double pages) {
        this.pages = pages;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
