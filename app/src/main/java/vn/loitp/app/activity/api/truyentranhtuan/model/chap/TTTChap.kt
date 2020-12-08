package vn.loitp.app.activity.api.truyentranhtuan.model.chap

import androidx.annotation.Keep
import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class TTTChap : BaseModel() {
    @SerializedName("chaps")
    @Expose
    var chaps: Chaps? = null

    @SerializedName("info")
    @Expose
    var info: Info? = null

}
