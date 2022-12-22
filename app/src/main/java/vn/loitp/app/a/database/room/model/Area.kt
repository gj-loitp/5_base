package vn.loitp.app.a.database.room.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.Serializable

@Keep
data class Area(
    @Json(name = "id")
    var id: String? = null,

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "tables")
    var tables: List<Table>? = null
) : Serializable
