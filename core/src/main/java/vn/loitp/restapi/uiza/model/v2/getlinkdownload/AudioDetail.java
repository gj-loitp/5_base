
package vn.loitp.restapi.uiza.model.v2.getlinkdownload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AudioDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("audio_type")
    @Expose
    private String audioType;
    @SerializedName("bitrate")
    @Expose
    private String bitrate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("template")
    @Expose
    private String template;
    @SerializedName("enable_normalize_audio")
    @Expose
    private boolean enableNormalizeAudio;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAudioType() {
        return audioType;
    }

    public void setAudioType(String audioType) {
        this.audioType = audioType;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public boolean isEnableNormalizeAudio() {
        return enableNormalizeAudio;
    }

    public void setEnableNormalizeAudio(boolean enableNormalizeAudio) {
        this.enableNormalizeAudio = enableNormalizeAudio;
    }

}
