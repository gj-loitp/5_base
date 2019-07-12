package com.core.utilities

import android.app.Activity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

object LPopupMenu {
    interface CallBack {
        fun clickOnItem(menuItem: MenuItem)
    }

    fun show(activity: Activity, showOnView: View, menuRes: Int, callBack: CallBack?) {
        val popup = PopupMenu(activity, showOnView)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            callBack?.clickOnItem(menuItem)
            true
        }
        popup.show()
    }
}
