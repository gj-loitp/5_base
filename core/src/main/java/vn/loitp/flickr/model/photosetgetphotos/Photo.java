
package vn.loitp.flickr.model.photosetgetphotos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private double farm;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("isprimary")
    @Expose
    private String isprimary;
    @SerializedName("ispublic")
    @Expose
    private double ispublic;
    @SerializedName("isfriend")
    @Expose
    private double isfriend;
    @SerializedName("isfamily")
    @Expose
    private double isfamily;
    @SerializedName("url_o")
    @Expose
    private String urlO;
    @SerializedName("height_o")
    @Expose
    private String heightO;
    @SerializedName("width_o")
    @Expose
    private String widthO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public double getFarm() {
        return farm;
    }

    public void setFarm(double farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsprimary() {
        return isprimary;
    }

    public void setIsprimary(String isprimary) {
        this.isprimary = isprimary;
    }

    public double getIspublic() {
        return ispublic;
    }

    public void setIspublic(double ispublic) {
        this.ispublic = ispublic;
    }

    public double getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(double isfriend) {
        this.isfriend = isfriend;
    }

    public double getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(double isfamily) {
        this.isfamily = isfamily;
    }

    public String getUrlO() {
        return urlO;
    }

    public void setUrlO(String urlO) {
        this.urlO = urlO;
    }

    public String getHeightO() {
        return heightO;
    }

    public void setHeightO(String heightO) {
        this.heightO = heightO;
    }

    public String getWidthO() {
        return widthO;
    }

    public void setWidthO(String widthO) {
        this.widthO = widthO;
    }

}
