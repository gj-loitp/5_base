package vn.puresolutions.livestar.core.api.model;


import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Room extends BaseModel {
    private int id;
    private String title;
    private String slug;
    @SerializedName("thumb_mb")
    private String thumbApp;
    private String thumb;
    private String date;
    private String start;
    @SerializedName("on_air")
    private boolean onAir;
    private int totalUser;
    @SerializedName("thumb_poster")
    private String poster;

    private Broadcaster broadcaster;

    public Room() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Broadcaster getBroadcaster() {
        return broadcaster;
    }

    public void setBroadcaster(Broadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }


    public boolean isOnAir() {
        return onAir;
    }

    public void setOnAir(boolean onAir) {
        this.onAir = onAir;
    }

    public String getThumbApp() {
        return thumbApp;
    }

    public void setThumbApp(String thumbApp) {
        this.thumbApp = thumbApp;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
