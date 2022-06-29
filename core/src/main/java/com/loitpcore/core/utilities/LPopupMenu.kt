package com.loitpcore.core.utilities

import android.app.Activity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu

class LPopupMenu {

    companion object {

        fun show(
            activity: Activity,
            showOnView: View,
            menuRes: Int,
            callback: ((MenuItem) -> Unit)? = null
        ) {
            val popup = PopupMenu(activity, showOnView)
            popup.menuInflater.inflate(menuRes, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                callback?.invoke(menuItem)
                true
            }
            popup.show()
        }
    }
}
