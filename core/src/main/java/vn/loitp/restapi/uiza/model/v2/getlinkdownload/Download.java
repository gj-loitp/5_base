
package vn.loitp.restapi.uiza.model.v2.getlinkdownload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Download {

    @SerializedName("linkDownload")
    @Expose
    private String linkDownload;
    @SerializedName("profileName")
    @Expose
    private String profileName;
    @SerializedName("videoDetail")
    @Expose
    private VideoDetail videoDetail;
    @SerializedName("resolution")
    @Expose
    private String resolution;
    @SerializedName("audioDetail")
    @Expose
    private AudioDetail audioDetail;

    public String getLinkDownload() {
        return linkDownload;
    }

    public void setLinkDownload(String linkDownload) {
        this.linkDownload = linkDownload;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public VideoDetail getVideoDetail() {
        return videoDetail;
    }

    public void setVideoDetail(VideoDetail videoDetail) {
        this.videoDetail = videoDetail;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public AudioDetail getAudioDetail() {
        return audioDetail;
    }

    public void setAudioDetail(AudioDetail audioDetail) {
        this.audioDetail = audioDetail;
    }

}
