package com.core.helper.mup.comic.model

import androidx.annotation.Keep
import com.R
import com.core.utilities.LAppResource
import java.io.Serializable

@Keep
data class Category(
        val createdBy: String? = null,
        val createdDate: String? = null,
        val description: String? = null,
        val id: String? = null,
        val isDelete: Boolean? = null,
        val modifiedBy: String? = null,
        val modifiedDate: String? = null,
        val title: String? = null,
        val urlSource: String? = null,
        var isSelected: Boolean = false
) : Serializable {
    companion object {
        fun getCategoryAll(): Category {
            return Category(
                    id = "69696969",
                    title = LAppResource.getString(R.string.all_vn)
            )
        }
    }
}
