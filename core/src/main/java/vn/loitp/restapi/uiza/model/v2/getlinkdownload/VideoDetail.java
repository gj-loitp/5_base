
package vn.loitp.restapi.uiza.model.v2.getlinkdownload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bitrate")
    @Expose
    private String bitrate;
    @SerializedName("bufsize")
    @Expose
    private int bufsize;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("framerate")
    @Expose
    private String framerate;
    @SerializedName("keyint")
    @Expose
    private String keyint;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("maxrate")
    @Expose
    private int maxrate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("preset")
    @Expose
    private String preset;
    @SerializedName("template")
    @Expose
    private String template;
    @SerializedName("vprofile")
    @Expose
    private String vprofile;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("enable_hevc")
    @Expose
    private boolean enableHevc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public int getBufsize() {
        return bufsize;
    }

    public void setBufsize(int bufsize) {
        this.bufsize = bufsize;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getFramerate() {
        return framerate;
    }

    public void setFramerate(String framerate) {
        this.framerate = framerate;
    }

    public String getKeyint() {
        return keyint;
    }

    public void setKeyint(String keyint) {
        this.keyint = keyint;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getMaxrate() {
        return maxrate;
    }

    public void setMaxrate(int maxrate) {
        this.maxrate = maxrate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getVprofile() {
        return vprofile;
    }

    public void setVprofile(String vprofile) {
        this.vprofile = vprofile;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isEnableHevc() {
        return enableHevc;
    }

    public void setEnableHevc(boolean enableHevc) {
        this.enableHevc = enableHevc;
    }

}
