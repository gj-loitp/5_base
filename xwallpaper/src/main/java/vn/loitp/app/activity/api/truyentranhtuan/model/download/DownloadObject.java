package vn.loitp.app.activity.api.truyentranhtuan.model.download;

/**
 * Created by Loitp on 4/8/2017.
 */

public class DownloadObject {
    public static String STATUS_IS_WAITING = "Đang chờ";
    public static String STATUS_IS_DOWNLOADING = "Đang tải";
    public static String STATUS_DOWNLOADED_SUCCESS = "Đã tải thành công";
    public static String STATUS_DOWNLOADED_FAIL = "Đã tải thất bại";

    private String url;
    private String tit;
    private String status;
    private int progress;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
