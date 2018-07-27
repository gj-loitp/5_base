
package vn.loitp.app.activity.demo.chromecast.browser.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("sources")
    @Expose
    private List<Source> sources = null;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("image-480x270")
    @Expose
    private String image480x270;
    @SerializedName("image-780x1200")
    @Expose
    private String image780x1200;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("studio")
    @Expose
    private String studio;
    @SerializedName("duration")
    @Expose
    private long duration;
    @SerializedName("tracks")
    @Expose
    private List<Track> tracks = null;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage480x270() {
        return image480x270;
    }

    public void setImage480x270(String image480x270) {
        this.image480x270 = image480x270;
    }

    public String getImage780x1200() {
        return image780x1200;
    }

    public void setImage780x1200(String image780x1200) {
        this.image780x1200 = image780x1200;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

}
