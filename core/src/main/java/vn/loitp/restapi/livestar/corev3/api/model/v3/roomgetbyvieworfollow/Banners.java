
package vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.BaseModel;

public class Banners extends BaseModel {
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("w1080h622")
    @Expose
    private String w1080h622;
    @SerializedName("w512h512")
    @Expose
    private String w512h512;
    @SerializedName("w720h415")
    @Expose
    private String w720h415;
    @SerializedName("w330h330")
    @Expose
    private String w330h330;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getW1080h622() {
        return w1080h622;
    }

    public void setW1080h622(String w1080h622) {
        this.w1080h622 = w1080h622;
    }

    public String getW512h512() {
        return w512h512;
    }

    public void setW512h512(String w512h512) {
        this.w512h512 = w512h512;
    }

    public String getW720h415() {
        return w720h415;
    }

    public void setW720h415(String w720h415) {
        this.w720h415 = w720h415;
    }

    public String getW330h330() {
        return w330h330;
    }

    public void setW330h330(String w330h330) {
        this.w330h330 = w330h330;
    }
}
