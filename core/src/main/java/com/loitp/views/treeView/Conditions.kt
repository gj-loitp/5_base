package com.loitp.views.treeView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
internal object Conditions {

    @JvmStatic
    fun <T> isNonNull(
        t: T?,
        message: String
    ): T {
        requireNotNull(t) { message }
        return t
    }
}
