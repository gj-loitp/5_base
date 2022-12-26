package com.loitp.game.findNumber.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitp.game.findNumber.db.Db.Companion.STATUS_LEVEL_OPEN
import java.io.Serializable

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
@Entity(tableName = "Level")
data class Level(
    @SerializedName("id")
    @Expose
    @PrimaryKey
    var id: String = "",

    @SerializedName("name")
    @Expose
    var name: Int = 1,

    @SerializedName("col")
    @Expose
    var col: Int = 2,

    @SerializedName("row")
    @Expose
    var row: Int = 2,

    @SerializedName("rotate")
    @Expose
    var rotate: Float = 0f,

    @SerializedName("frame")
    @Expose
    var frame: Int = 0,

    @SerializedName("bkg")
    @Expose
    var bkg: Int = 0,

    @SerializedName("status")
    @Expose
    var status: Int = STATUS_LEVEL_OPEN,

    @SerializedName("timeInMls")
    @Expose
    var timeInMls: Long = 0

) : Serializable
