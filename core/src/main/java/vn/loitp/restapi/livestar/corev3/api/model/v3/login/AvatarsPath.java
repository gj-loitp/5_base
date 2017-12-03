package vn.loitp.restapi.livestar.corev3.api.model.v3.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.BaseModel;

/**
 * File created on 8/14/2017.
 *
 * @author anhdv
 */

public class AvatarsPath extends BaseModel {
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("w60h60")
    @Expose
    private String w60h60;
    @SerializedName("w160h160")
    @Expose
    private String w160h160;
    @SerializedName("w300h300")
    @Expose
    private String w300h300;
    @SerializedName("w512h512")
    @Expose
    private String w512h512;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getW60h60() {
        return w60h60;
    }

    public void setW60h60(String w60h60) {
        this.w60h60 = w60h60;
    }

    public String getW160h160() {
        return w160h160;
    }

    public void setW160h160(String w160h160) {
        this.w160h160 = w160h160;
    }

    public String getW300h300() {
        return w300h300;
    }

    public void setW300h300(String w300h300) {
        this.w300h300 = w300h300;
    }

    public String getW512h512() {
        return w512h512;
    }

    public void setW512h512(String w512h512) {
        this.w512h512 = w512h512;
    }

}
