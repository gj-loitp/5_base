package vn.puresolutions.livestar.corev3.api.model.old;

import com.google.gson.annotations.SerializedName;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/23/2015
 */
public class Vip extends BaseModel {
    private int vip;
    @SerializedName("no_char")
    private int noChar;
    @SerializedName("screen_time")
    private int screenTime;

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getScreenTime() {
        return screenTime;
    }

    public void setScreenTime(int screenTime) {
        this.screenTime = screenTime;
    }

    public int getNoChar() {
        return noChar;
    }

    public void setNoChar(int noChar) {
        this.noChar = noChar;
    }
}
