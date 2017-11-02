
package vn.loitp.app.activity.api.truyentranhtuan.model.chap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chap {

    @SerializedName("tit")
    @Expose
    private String tit;

    @SerializedName("url")
    @Expose

    private String url;

    /**
     * @return The tit
     */
    public String getTit() {
        return tit;
    }

    /**
     * @param tit The tit
     */
    public void setTit(String tit) {
        this.tit = tit;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
