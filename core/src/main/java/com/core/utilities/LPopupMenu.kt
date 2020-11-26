package com.core.utilities

import android.app.Activity
import android.view.View
import android.widget.PopupMenu
import com.interfaces.CallbackPopup

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

class LPopupMenu {

    companion object {

        fun show(
                activity: Activity,
                showOnView: View,
                menuRes: Int,
                callBackPopup: CallbackPopup?
        ) {
            val popup = PopupMenu(activity, showOnView)
            popup.menuInflater.inflate(menuRes, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                callBackPopup?.clickOnItem(menuItem)
                true
            }
            popup.show()
        }
    }
}
