package com.loitpcore.views.treeView

internal object Conditions {

    @JvmStatic
    fun <T> isNonNull(t: T?, message: String): T {
        requireNotNull(t) { message }
        return t
    }
}
