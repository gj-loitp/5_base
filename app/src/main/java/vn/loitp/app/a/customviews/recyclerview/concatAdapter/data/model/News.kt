package vn.loitp.app.a.customviews.recyclerview.concatAdapter.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class News(
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("title")
    var title: String = "",

    @SerializedName("image")
    var image: String = ""
) : Serializable
