package vn.loitp.app.activity.api.truyentranhtuan.model.download

import androidx.annotation.Keep
import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class Chap : BaseModel() {
    @SerializedName("tit")
    @Expose
    var tit: String = ""

    @SerializedName("size")
    @Expose
    var size = 0

}
