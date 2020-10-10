package vn.loitp.app.activity.api.truyentranhtuan.model.comic

import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Comic : BaseModel() {
    @SerializedName("tvTitle")
    @Expose
    var title: String = ""

    @SerializedName("url")
    @Expose
    var url: String = ""

    @SerializedName("tvDate")
    @Expose
    var date: String = ""

    @SerializedName("urlImg")
    @Expose
    var urlImg: String = ""

    @SerializedName("type")
    @Expose
    var type: String = ""

}
