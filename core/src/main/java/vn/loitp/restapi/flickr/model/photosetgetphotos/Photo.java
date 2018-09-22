
package vn.loitp.restapi.flickr.model.photosetgetphotos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import vn.loitp.core.utilities.LImageUtil;

public class Photo implements Serializable {

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
    private int heightO;
    @SerializedName("width_o")
    @Expose
    private int widthO;
    @SerializedName("url_s")
    @Expose
    private String urlS;
    @SerializedName("height_s")
    @Expose
    private int heightS;
    @SerializedName("width_s")
    @Expose
    private int widthS;
    @SerializedName("url_m")
    @Expose
    private String urlM;
    @SerializedName("height_m")
    @Expose
    private int heightM;
    @SerializedName("width_m")
    @Expose
    private int widthM;

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

    public int getHeightO() {
        return heightO;
    }

    public void setHeightO(int heightO) {
        this.heightO = heightO;
    }

    public int getWidthO() {
        return widthO;
    }

    public void setWidthO(int widthO) {
        this.widthO = widthO;
    }

    public String getUrlS() {
        return urlS;
    }

    public void setUrlS(String urlS) {
        this.urlS = urlS;
    }

    public int getHeightS() {
        return heightS;
    }

    public void setHeightS(int heightS) {
        this.heightS = heightS;
    }

    public int getWidthS() {
        return widthS;
    }

    public void setWidthS(int widthS) {
        this.widthS = widthS;
    }

    public String getUrlM() {
        return urlM;
    }

    public void setUrlM(String urlM) {
        this.urlM = urlM;
    }

    public int getHeightM() {
        return heightM;
    }

    public void setHeightM(int heightM) {
        this.heightM = heightM;
    }

    public int getWidthM() {
        return widthM;
    }

    public void setWidthM(int widthM) {
        this.widthM = widthM;
    }

    public String getFlickrLink100() {
        if (urlO.contains(".gif")) {
            //gif extension have no link large
            return urlO;
        } else {
            return LImageUtil.getFlickrLink100(urlM);
        }
    }

    public String getFlickrLink640() {
        if (urlO.contains(".gif")) {
            //gif extension have no link large
            return urlO;
        } else {
            return LImageUtil.getFlickrLink640(urlM);
        }
    }

    public String getFlickrLink1024() {
        if (urlO.contains(".gif")) {
            //gif extension have no link large
            return urlO;
        } else {
            return LImageUtil.getFlickrLink1024(urlM);
        }
    }

    public String getFlickrLink320() {
        if (urlO.contains(".gif")) {
            //gif extension have no link large
            return urlO;
        } else {
            return LImageUtil.getFlickrLink320(urlM);
        }
    }
}
