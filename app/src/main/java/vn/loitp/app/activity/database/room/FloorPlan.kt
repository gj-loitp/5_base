package vn.loitp.app.activity.database.room

import androidx.annotation.Keep
import androidx.room.*
import com.squareup.moshi.Json
import java.io.Serializable

@Keep
@Entity(tableName = "floorPlan")
@TypeConverters(AreaConverter::class)
data class FloorPlan(

        @Json(name = "id")
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: String = "",

        @Json(name = "name")
        @ColumnInfo(name = "name")
        var name: String? = null,

        @Json(name = "status")
        @ColumnInfo(name = "status")
        var status: Int? = null,

        @Json(name = "outletId")
        @ColumnInfo(name = "outletId")
        var outletId: String? = null,

        @Json(name = "areas")
        var areas: List<Area>? = null,

        @Ignore
        var isCheck: Boolean = false

) : Serializable
