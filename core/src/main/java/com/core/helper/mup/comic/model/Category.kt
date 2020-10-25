package com.core.helper.mup.comic.model

import androidx.annotation.Keep
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
        private const val ID_CATEGORY_ALL = "69696969"
        private const val TITLE_CATEGORY_ALL = "Tất cả"

        fun getCategoryAll(): Category {
            return Category(
                    id = ID_CATEGORY_ALL,
                    title = TITLE_CATEGORY_ALL
            )
        }

        fun isCategoryAll(category: Category): Boolean {
            if (category.id == ID_CATEGORY_ALL && category.title == TITLE_CATEGORY_ALL) {
                return true
            }
            return false
        }
    }
}
