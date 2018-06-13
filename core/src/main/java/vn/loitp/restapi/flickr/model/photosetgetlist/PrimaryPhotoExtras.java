
package vn.loitp.restapi.flickr.model.photosetgetlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrimaryPhotoExtras {

    @SerializedName("url_o")
    @Expose
    private String urlO;
    @SerializedName("height_o")
    @Expose
    private int heightO;
    @SerializedName("width_o")
    @Expose
    private int widthO;
    @SerializedName("url_m")
    @Expose
    private String urlM;
    @SerializedName("height_m")
    @Expose
    private int heightM;
    @SerializedName("width_m")
    @Expose
    private int widthM;

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

}
