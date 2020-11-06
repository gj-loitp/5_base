package vn.loitp.app.activity.api.truyentranhtuan.model.comic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Comic")
class Comic : BaseModel() {
    @ColumnInfo(name = "title")
    @SerializedName("tvTitle")
    @Expose
    var title: String = ""

    @PrimaryKey
    @ColumnInfo(name = "url")
    @SerializedName("url")
    @Expose
    var url: String = ""

    @ColumnInfo(name = "tvDate")
    @SerializedName("tvDate")
    @Expose
    var date: String = ""

    @ColumnInfo(name = "urlImg")
    @SerializedName("urlImg")
    @Expose
    var urlImg: String = ""

    @ColumnInfo(name = "type")
    @SerializedName("type")
    @Expose
    var type: String = ""

}
