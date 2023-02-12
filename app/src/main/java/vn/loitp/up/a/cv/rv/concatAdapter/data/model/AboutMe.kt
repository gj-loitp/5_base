package vn.loitp.up.a.cv.rv.concatAdapter.data.model

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

@Keep
data class AboutMe(
    val id: Int = 0,
    var name: String = "",
    var aboutMe: String = ""
) : BaseModel()
