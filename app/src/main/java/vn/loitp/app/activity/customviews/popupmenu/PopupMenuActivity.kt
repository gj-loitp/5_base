package vn.loitp.app.activity.customviews.popupmenu

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LPopupMenu
import com.views.LToast
import loitp.basemaster.R

class PopupMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_show_1).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_2).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_3).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_4).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_5).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_popup_menu
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_show_1,
            R.id.bt_show_2,
            R.id.bt_show_3,
            R.id.bt_show_4,
            R.id.bt_show_5 -> LPopupMenu.show(activity, v, R.menu.menu_popup, object : LPopupMenu.CallBack {
                override fun clickOnItem(menuItem: MenuItem) {
                    LToast.show(activity, menuItem.getTitle().toString())
                }
            })
        }
    }
}
