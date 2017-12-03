
package vn.loitp.restapi.flickr.model.photosetgetlist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photosets {

    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("pages")
    @Expose
    private double pages;
    @SerializedName("perpage")
    @Expose
    private String perpage;
    @SerializedName("total")
    @Expose
    private double total;
    @SerializedName("photoset")
    @Expose
    private List<Photoset> photoset = null;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public double getPages() {
        return pages;
    }

    public void setPages(double pages) {
        this.pages = pages;
    }

    public String getPerpage() {
        return perpage;
    }

    public void setPerpage(String perpage) {
        this.perpage = perpage;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Photoset> getPhotoset() {
        return photoset;
    }

    public void setPhotoset(List<Photoset> photoset) {
        this.photoset = photoset;
    }

}
