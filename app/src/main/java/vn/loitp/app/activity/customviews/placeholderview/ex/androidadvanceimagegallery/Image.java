package vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

public class Image {

    @SerializedName("url")
    @Expose
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}