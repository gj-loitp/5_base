
package vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbycategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.BaseModel;

public class Backgrounds extends BaseModel {

    @SerializedName("background")
    @Expose
    private Object background;
    @SerializedName("background_w190h108")
    @Expose
    private Object backgroundW190h108;
    @SerializedName("background_w380h216")
    @Expose
    private Object backgroundW380h216;
    @SerializedName("background_w760h432")
    @Expose
    private Object backgroundW760h432;
    @SerializedName("background_w160h190")
    @Expose
    private Object backgroundW160h190;
    public Object getBackground() {
        return background;
    }

    public void setBackground(Object background) {
        this.background = background;
    }

    public Object getBackgroundW190h108() {
        return backgroundW190h108;
    }

    public void setBackgroundW190h108(Object backgroundW190h108) {
        this.backgroundW190h108 = backgroundW190h108;
    }

    public Object getBackgroundW380h216() {
        return backgroundW380h216;
    }

    public void setBackgroundW380h216(Object backgroundW380h216) {
        this.backgroundW380h216 = backgroundW380h216;
    }

    public Object getBackgroundW760h432() {
        return backgroundW760h432;
    }

    public void setBackgroundW760h432(Object backgroundW760h432) {
        this.backgroundW760h432 = backgroundW760h432;
    }
    public Object getBackgroundW160h190() {
        return backgroundW160h190;
    }

    public void setBackgroundW160h190(Object backgroundW160h190) {
        this.backgroundW160h190 = backgroundW160h190;
    }

}
