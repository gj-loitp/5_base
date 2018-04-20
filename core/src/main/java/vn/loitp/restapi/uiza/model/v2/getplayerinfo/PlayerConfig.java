
package vn.loitp.restapi.uiza.model.v2.getplayerinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerConfig {

    @SerializedName("setting")
    @Expose
    private Setting setting;
    @SerializedName("styling")
    @Expose
    private Styling styling;
    @SerializedName("socialSharing")
    @Expose
    private SocialSharing socialSharing;
    @SerializedName("endscreen")
    @Expose
    private Endscreen endscreen;

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public Styling getStyling() {
        return styling;
    }

    public void setStyling(Styling styling) {
        this.styling = styling;
    }

    public SocialSharing getSocialSharing() {
        return socialSharing;
    }

    public void setSocialSharing(SocialSharing socialSharing) {
        this.socialSharing = socialSharing;
    }

    public Endscreen getEndscreen() {
        return endscreen;
    }

    public void setEndscreen(Endscreen endscreen) {
        this.endscreen = endscreen;
    }

}
