
package vn.loitp.restapi.uiza.model.v2.getplayerinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Setting {

    @SerializedName("autoStart")
    @Expose
    private String autoStart;
    @SerializedName("allowFullscreen")
    @Expose
    private String allowFullscreen;
    @SerializedName("showQuality")
    @Expose
    private String showQuality;
    @SerializedName("displayPlaylist")
    @Expose
    private String displayPlaylist;

    public String getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(String autoStart) {
        this.autoStart = autoStart;
    }

    public String getAllowFullscreen() {
        return allowFullscreen;
    }

    public void setAllowFullscreen(String allowFullscreen) {
        this.allowFullscreen = allowFullscreen;
    }

    public String getShowQuality() {
        return showQuality;
    }

    public void setShowQuality(String showQuality) {
        this.showQuality = showQuality;
    }

    public String getDisplayPlaylist() {
        return displayPlaylist;
    }

    public void setDisplayPlaylist(String displayPlaylist) {
        this.displayPlaylist = displayPlaylist;
    }

}
