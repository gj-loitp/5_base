package vn.loitp.up.a.cv.rv.concatAdapter.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.loitp.core.base.BaseModel

@Keep
data class News(
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("title")
    var title: String = "",

    @SerializedName("image")
    var image: String = ""
) : BaseModel()
