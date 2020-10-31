package com.core.helper.mup.comic.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Chap(
//        val chapterComicsDetails: Any,
        val comicsId: String? = null,
        val createdBy: String? = null,
        val createdDate: String? = null,
        val description: String? = null,
        val id: String? = null,
        val isDelete: Boolean? = null,
        val modifiedBy: String? = null,
        val modifiedDate: String? = null,
//        val nextChap: Any? = null,
        val noChapter: Int? = null,
//        val prevChap: Any? = null,
        val slug: String? = null,
        val title: String? = null
) : Serializable
