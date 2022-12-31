package vn.loitp.a.cv.rv.concatAdapter.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.loitp.core.base.BaseModel

@Keep
data class User(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("avatar")
    var avatar: String = ""
) : BaseModel()
