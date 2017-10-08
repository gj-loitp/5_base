
package vn.loitp.flickr.model.photosetgetphotos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WrapperPhotosetGetPhotos {

    @SerializedName("photoset")
    @Expose
    private Photoset photoset;
    @SerializedName("stat")
    @Expose
    private String stat;

    public Photoset getPhotoset() {
        return photoset;
    }

    public void setPhotoset(Photoset photoset) {
        this.photoset = photoset;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
