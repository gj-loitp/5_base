package vn.loitp.app.activity.tutorial.retrofit2

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitp.core.base.BaseModel

@Keep
class RetroCrypto : BaseModel() {
    @SerializedName("currency")
    @Expose
    var currency: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null
}
