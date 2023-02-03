package vn.loitp.up.a.db.room.md

import androidx.annotation.Keep
import androidx.room.*
import com.squareup.moshi.Json
import vn.loitp.up.a.db.room.converter.AreaConverter
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

    @Json(name = "areas")
    var areas: List<Area>? = null,

    @Ignore // wont save to room
    var isCheck: Boolean = false

) : Serializable
