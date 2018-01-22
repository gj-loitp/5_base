package vn.loitp.app.model.comic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Comic extends RealmObject {
    private int id;

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
        //TODO remove
        if (urlImg == null) {
            return "https://kenh14cdn.com/2017/photo-3-1508474775887.jpg";
        }
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //0 true, !=0 false
    private int isFav;

    public int isFav() {
        return isFav;
    }

    public void setFav(int fav) {
        isFav = fav;
    }
}
