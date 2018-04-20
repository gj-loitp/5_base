
package vn.loitp.restapi.uiza.model.v1.getlinkplay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetLinkPlay {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("linkplay_hls")
    @Expose
    private String linkplayHls;
    @SerializedName("linkplay_mpd")
    @Expose
    private String linkplayMpd;
    @SerializedName("hls")
    @Expose
    private List<String> hls = null;
    @SerializedName("mpd")
    @Expose
    private List<String> mpd = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLinkplayHls() {
        return linkplayHls;
    }

    public void setLinkplayHls(String linkplayHls) {
        this.linkplayHls = linkplayHls;
    }

    public String getLinkplayMpd() {
        return linkplayMpd;
    }

    public void setLinkplayMpd(String linkplayMpd) {
        this.linkplayMpd = linkplayMpd;
    }

    public List<String> getHls() {
        return hls;
    }

    public void setHls(List<String> hls) {
        this.hls = hls;
    }

    public List<String> getMpd() {
        if (mpd == null) {
            mpd = new ArrayList<>();
            if (linkplayMpd != null) {
                mpd.add(linkplayMpd);
            }
        }
        return mpd;
    }

    public void setMpd(List<String> mpd) {
        this.mpd = mpd;
    }

}
