
package vn.loitp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {

    @SerializedName("uiza")
    @Expose
    private Uiza uiza;
    @SerializedName("isReady")
    @Expose
    private boolean isReady;
    @SerializedName("isFullData")
    @Expose
    private boolean isFullData;
    @SerializedName("bkgSplash")
    @Expose
    private String bkgSplash;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Uiza getUiza() {
        return uiza;
    }

    public void setUiza(Uiza uiza) {
        this.uiza = uiza;
    }

    public boolean isIsReady() {
        return isReady;
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }

    public String getBkgSplash() {
        return bkgSplash;
    }

    public void setBkgSplash(String bkgSplash) {
        this.bkgSplash = bkgSplash;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isFullData() {
        return isFullData;
    }

    public void setFullData(boolean fullData) {
        isFullData = fullData;
    }
}
