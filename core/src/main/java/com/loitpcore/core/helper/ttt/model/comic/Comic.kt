package com.loitpcore.core.helper.ttt.model.comic

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitp.core.base.BaseModel

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
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
    var type: String? = ""

    @ColumnInfo(name = "isFav")
    @SerializedName("isFav")
    @Expose
    var isFav: Boolean = false
}
