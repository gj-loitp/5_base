package vn.puresolutions.livestar.corev3.api.model.old;


import com.google.gson.annotations.SerializedName;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/23/2015
 */
public class VipPackageDetails extends BaseModel {
    private String name;
    private String image;
    @SerializedName("no_char")
    private int characterNumber;
    @SerializedName("screen_text_time")
    private int screenTextTime;
    @SerializedName("screen_text_effect")
    private String screenTextEffect;
    @SerializedName("kick_level")
    private String kickLevel;
    @SerializedName("clock_kick")
    private String clockKick;
    @SerializedName("clock_ads")
    private boolean clockAds;
    @SerializedName("exp_bonus")
    private float expBonus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCharacterNumber() {
        return characterNumber;
    }

    public void setCharacterNumber(int characterNumber) {
        this.characterNumber = characterNumber;
    }

    public int getScreenTextTime() {
        return screenTextTime;
    }

    public void setScreenTextTime(int screenTextTime) {
        this.screenTextTime = screenTextTime;
    }

    public String getScreenTextEffect() {
        return screenTextEffect;
    }

    public void setScreenTextEffect(String screenTextEffect) {
        this.screenTextEffect = screenTextEffect;
    }

    public String getKickLevel() {
        return kickLevel;
    }

    public void setKickLevel(String kickLevel) {
        this.kickLevel = kickLevel;
    }

    public String getClockKick() {
        return clockKick;
    }

    public void setClockKick(String clockKick) {
        this.clockKick = clockKick;
    }

    public boolean isClockAds() {
        return clockAds;
    }

    public void setClockAds(boolean clockAds) {
        this.clockAds = clockAds;
    }

    public float getExpBonus() {
        return expBonus;
    }

    public void setExpBonus(float expBonus) {
        this.expBonus = expBonus;
    }

}
