package vn.loitp.app.a.cv.recyclerview.diffUtil

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Content(val id: Int, val text: String, val image: String) : Serializable
