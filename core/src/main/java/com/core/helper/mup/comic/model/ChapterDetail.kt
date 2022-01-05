package com.core.helper.mup.comic.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class ChapterDetail(
    @SerializedName("chapterComicsDetails")
    @Expose
    val chapterComicsDetails: List<ChapterComicsDetail>,

    @SerializedName("comicsId")
    @Expose
    val comicsId: String? = null,

    @SerializedName("createdBy")
    @Expose
    val createdBy: String? = null,

    @SerializedName("createdDate")
    @Expose
    val createdDate: String? = null,

    @SerializedName("description")
    @Expose
    val description: String? = null,

    @SerializedName("id")
    @Expose
    val id: String? = null,

    @SerializedName("isDelete")
    @Expose
    val isDelete: Boolean? = null,

    @SerializedName("modifiedBy")
    @Expose
    val modifiedBy: String? = null,

    @SerializedName("modifiedDate")
    @Expose
    val modifiedDate: String? = null,

    @SerializedName("nextChap")
    @Expose
    val nextChap: Chap? = null,

    @SerializedName("noChapter")
    @Expose
    val noChapter: Int? = null,

    @SerializedName("prevChap")
    @Expose
    val prevChap: Chap? = null,

    @SerializedName("slug")
    @Expose
    val slug: String? = null,

    @SerializedName("title")
    @Expose
    val title: String? = null
) : Serializable
