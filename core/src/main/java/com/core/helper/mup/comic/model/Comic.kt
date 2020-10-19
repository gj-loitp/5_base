package com.core.helper.mup.comic.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Comic(
        val createdBy: String? = null,
        val createdDate: String? = null,
        val description: String? = null,
        val id: String? = null,
        val imageId: String? = null,
        val imageSrc: String? = null,
        val isDelete: Boolean? = null,
        val modifiedBy: String? = null,
        val modifiedDate: String? = null,
        val title: String? = null,
        val totalChapter: Int? = null,
        val viewCount: Int? = null
) : Serializable
