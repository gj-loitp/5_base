package com.core.helper.mup.comic.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class Category(
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

        @SerializedName("title")
        @Expose
        val title: String? = null,

        @SerializedName("urlSource")
        @Expose
        val urlSource: String? = null,

        @SerializedName("isSelected")
        @Expose
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
