
package vn.loitp.flickr.model.photosetgetlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WrapperPhotosetGetlist {

    @SerializedName("photosets")
    @Expose
    private Photosets photosets;
    @SerializedName("stat")
    @Expose
    private String stat;

    public Photosets getPhotosets() {
        return photosets;
    }

    public void setPhotosets(Photosets photosets) {
        this.photosets = photosets;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
