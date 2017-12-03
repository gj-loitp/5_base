
package vn.loitp.restapi.livestar.corev3.api.model.v3.bank;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.BaseModel;

public class Iap extends BaseModel {

    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("Sources")
    @Expose
    private List<Source> sources = null;
    @SerializedName("Packages")
    @Expose
    private ArrayList<PackagePayment> packages = null;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public ArrayList<PackagePayment> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<PackagePayment> packages) {
        this.packages = packages;
    }

}
