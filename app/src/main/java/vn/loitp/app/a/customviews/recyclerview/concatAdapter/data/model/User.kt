package vn.loitp.app.a.customviews.recyclerview.concatAdapter.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class User(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("avatar")
    var avatar: String = ""
) : Serializable
