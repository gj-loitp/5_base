
package vn.loitp.app.activity.api.truyentranhtuan.model.download;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadedWrapper {

    @SerializedName("downloadedComic")
    @Expose
    private List<DownloadedComic> downloadedComic = null;

    public List<DownloadedComic> getDownloadedComic() {
        return downloadedComic;
    }

    public void setDownloadedComic(List<DownloadedComic> downloadedComic) {
        this.downloadedComic = downloadedComic;
    }

}
