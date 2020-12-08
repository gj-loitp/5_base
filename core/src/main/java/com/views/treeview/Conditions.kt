package com.views.treeview

internal object Conditions {

    @JvmStatic
    fun <T> isNonNull(t: T?, message: String): T {
        requireNotNull(t) { message }
        return t
    }
}
