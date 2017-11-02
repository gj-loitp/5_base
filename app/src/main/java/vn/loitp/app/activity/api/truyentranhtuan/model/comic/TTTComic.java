
package vn.loitp.app.activity.api.truyentranhtuan.model.comic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TTTComic {

    @SerializedName("comics")
    @Expose
    private Comics comics;
    @SerializedName("time_upload")
    @Expose
    private String timeUpload;

    /**
     * 
     * @return
     *     The comics
     */
    public Comics getComics() {
        return comics;
    }

    /**
     * 
     * @param comics
     *     The comics
     */
    public void setComics(Comics comics) {
        this.comics = comics;
    }

    /**
     * 
     * @return
     *     The timeUpload
     */
    public String getTimeUpload() {
        return timeUpload;
    }

    /**
     * 
     * @param timeUpload
     *     The time_upload
     */
    public void setTimeUpload(String timeUpload) {
        this.timeUpload = timeUpload;
    }

}
