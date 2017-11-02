
package vn.loitp.app.activity.api.truyentranhtuan.model.download;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadedComic {
    @SerializedName("urlImg")
    @Expose
    private String urlImg;

    @SerializedName("tit")
    @Expose
    private String tit;

    @SerializedName("chaps")
    @Expose
    private List<Chap> chaps = null;

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public List<Chap> getChaps() {
        return chaps;
    }

    public void setChaps(List<Chap> chaps) {
        this.chaps = chaps;
    }

}
