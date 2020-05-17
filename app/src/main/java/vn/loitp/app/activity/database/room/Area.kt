package vn.loitp.app.activity.database.room

import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.Serializable

@Keep
data class Area(
        @Json(name = "id")
        val id: String? = null,

        @Json(name = "name")
        val name: String? = null,

        @Json(name = "status")
        val status: Int? = null,

        @Json(name = "outletId")
        val outletId: String? = null,

        @Json(name = "floorPlanId")
        val floorPlanId: String? = null,

        @Json(name = "tables")
        val tables: List<Table>? = null
) : Serializable