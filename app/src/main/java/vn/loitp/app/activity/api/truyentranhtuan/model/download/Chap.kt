package vn.loitp.app.activity.api.truyentranhtuan.model.download

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Chap {
    @SerializedName("tit")
    @Expose
    var tit: String = ""

    @SerializedName("size")
    @Expose
    var size = 0

}
