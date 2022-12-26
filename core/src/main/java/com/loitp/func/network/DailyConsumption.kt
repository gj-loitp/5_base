package com.loitp.func.network

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Entity(tableName = "daily_consumption")
data class DailyConsumption(
    @PrimaryKey
    var dayID: String,

    @ColumnInfo(name = "timestamp")
    var timestamp: Long,

    @ColumnInfo(name = "mobile")
    var mobile: Long,

    @ColumnInfo(name = "wifi")
    var wifi: Long,

    @ColumnInfo(name = "total")
    var total: Long
)
