package vn.loitp.app.activity.api.truyentranhtuan.model.comic

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TTTComic {

    @SerializedName("comics")
    @Expose
    var comics: Comics? = null

    @SerializedName("time_upload")
    @Expose
    var timeUpload: String = ""

}
