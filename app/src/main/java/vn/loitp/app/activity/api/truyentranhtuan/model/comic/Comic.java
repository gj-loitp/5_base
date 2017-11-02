package vn.loitp.app.activity.api.truyentranhtuan.model.comic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comic {

    @SerializedName("tvTitle")
    @Expose
    private String title;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("tvDate")
    @Expose
    private String date;

    @SerializedName("urlImg")
    @Expose
    private String urlImg;

    @SerializedName("type")
    @Expose
    private String type;

    /**
     * @return The tvTitle
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The tvTitle
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The url
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The url
     */
    public void setDate(String date) {
        this.date = date;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
