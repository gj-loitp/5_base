package vn.loitp.app.activity.api.truyentranhtuan.model.chap

import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Chap : BaseModel() {
    @SerializedName("tit")
    @Expose
    var tit: String = ""

    @SerializedName("url")
    @Expose
    var url: String = ""

}
