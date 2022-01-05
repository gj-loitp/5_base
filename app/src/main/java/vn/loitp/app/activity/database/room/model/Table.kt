package vn.loitp.app.activity.database.room.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.Serializable

@Keep
data class Table(
    @Json(name = "id")
    var id: String? = null,

    @Json(name = "name")
    var name: String? = null,

    // more property
    var isCheck: Boolean = false,
    var isExpand: Boolean = false

) : Serializable
