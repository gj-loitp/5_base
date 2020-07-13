package vn.loitp.app.activity.tutorial.retrofit2

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RetroCrypto {
    @SerializedName("currency")
    @Expose
    var currency: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null
}
