package vn.loitp.app.activity.api.truyentranhtuan.model.chap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TTTChap {
    @SerializedName("chaps")
    @Expose
    var chaps: Chaps? = null

    @SerializedName("info")
    @Expose
    var info: Info? = null

}
