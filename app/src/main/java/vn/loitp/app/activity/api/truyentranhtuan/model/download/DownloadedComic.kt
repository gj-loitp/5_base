package vn.loitp.app.activity.api.truyentranhtuan.model.download

import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DownloadedComic : BaseModel() {
    @SerializedName("urlImg")
    @Expose
    var urlImg: String = ""

    @SerializedName("tit")
    @Expose
    var tit: String = ""

    @SerializedName("chaps")
    @Expose
    var chaps = ArrayList<Chap>()

}
