package com.loitp.func.epub

import androidx.annotation.Keep
import java.io.Serializable

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class NavPoint : Serializable {

    companion object {
        private const val serialVersionUID = -5558515239198872045L
    }

    var id: String? = null
    var playOrder = 0
    var navLabel: String? = null
    var contentSrc: String? = null
    var type: String? = null
    var value: String? = null
    var isMarkedToDelete = false

    // Additional cropped file navPoint variables.
    var typeCode // 0 - realNavPoint, 1 - anchoredPart, 2 - trimmedPart
            = 0
    var entryName: String? = null
    var bodyTrimStartPosition = 0
    var bodyTrimEndPosition = 0
    var openTags: List<Tag>? = null
    var isCalculated = false

    override fun equals(other: Any?): Boolean {
        return when {
            contentSrc != null -> {
                contentSrc == (other as NavPoint?)?.contentSrc
            }
            entryName != null -> {
                entryName == (other as NavPoint?)?.entryName
            }
            else -> {
                false
            }
        }
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + playOrder
        result = 31 * result + (navLabel?.hashCode() ?: 0)
        result = 31 * result + (contentSrc?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + isMarkedToDelete.hashCode()
        result = 31 * result + typeCode
        result = 31 * result + (entryName?.hashCode() ?: 0)
        result = 31 * result + bodyTrimStartPosition
        result = 31 * result + bodyTrimEndPosition
        result = 31 * result + (openTags?.hashCode() ?: 0)
        result = 31 * result + isCalculated.hashCode()
        return result
    }
}
