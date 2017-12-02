package vn.loitp.app.activity.customviews.placeholderview.ex.androidinfinitelistwithloadmore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

public class InfiniteFeedInfo {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("caption")
    @Expose
    private String caption;

    @SerializedName("time")
    @Expose
    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}