package vn.loitp.app.activity.api.truyentranhtuan.model.comic

import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TTTComic : BaseModel() {

    @SerializedName("comics")
    @Expose
    var comics: Comics? = null

    @SerializedName("time_upload")
    @Expose
    var timeUpload: String = ""

}
