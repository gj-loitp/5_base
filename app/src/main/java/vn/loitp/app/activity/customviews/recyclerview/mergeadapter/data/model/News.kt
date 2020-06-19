package vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model

import com.google.gson.annotations.SerializedName

data class News(
        @SerializedName("id")
        val id: Long = 0,

        @SerializedName("title")
        var title: String = "",

        @SerializedName("image")
        var image: String = ""
)