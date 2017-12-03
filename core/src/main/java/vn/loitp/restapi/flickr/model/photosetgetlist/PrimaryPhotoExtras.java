
package vn.loitp.restapi.flickr.model.photosetgetlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrimaryPhotoExtras {

    @SerializedName("url_o")
    @Expose
    private String urlO;
    @SerializedName("height_o")
    @Expose
    private String heightO;
    @SerializedName("width_o")
    @Expose
    private String widthO;
    @SerializedName("url_m")
    @Expose
    private String urlM;
    @SerializedName("height_m")
    @Expose
    private String heightM;
    @SerializedName("width_m")
    @Expose
    private String widthM;

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

    public String getUrlM() {
        return urlM;
    }

    public void setUrlM(String urlM) {
        this.urlM = urlM;
    }

    public String getHeightM() {
        return heightM;
    }

    public void setHeightM(String heightM) {
        this.heightM = heightM;
    }

    public String getWidthM() {
        return widthM;
    }

    public void setWidthM(String widthM) {
        this.widthM = widthM;
    }

}
