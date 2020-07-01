package vn.loitp.app.activity.api.truyentranhtuan.model.comic

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Comics {
    @SerializedName("comic")
    @Expose
    var comic: List<Comic> = ArrayList()

}
