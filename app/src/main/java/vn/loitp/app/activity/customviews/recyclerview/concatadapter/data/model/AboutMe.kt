package vn.loitp.app.activity.customviews.recyclerview.concatadapter.data.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class AboutMe(
        val id: Int = 0,
        var name: String = "",
        var aboutMe: String = ""
) : Serializable
