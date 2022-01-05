package com.core.helper.mup.girl.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
@Entity(tableName = "GirlPage")
data class GirlPage(
    @SerializedName("src")
    @Expose
    val src: String?,

    @SerializedName("title")
    @Expose
    val title: String?,

    @SerializedName("id")
    @Expose
    @PrimaryKey
    val id: String = "",

    @SerializedName("createdDate")
    @Expose
    val createdDate: String?,

    @SerializedName("isFavorites")
    @Expose
    var isFavorites: Boolean = false
) : Serializable
