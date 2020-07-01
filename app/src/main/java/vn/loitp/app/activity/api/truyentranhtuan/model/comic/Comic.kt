package vn.loitp.app.activity.api.truyentranhtuan.model.comic

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Comic {
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
