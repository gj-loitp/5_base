package vn.loitp.app.activity.customviews.popupmenu

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LPopupMenu
import com.interfaces.CallbackPopup
import com.views.LToast
import kotlinx.android.synthetic.main.activity_menu_popup.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_menu_popup)
class PopupMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btShow1.setOnClickListener(this)
        btShow2.setOnClickListener(this)
        btShow3.setOnClickListener(this)
        btShow4.setOnClickListener(this)
        btShow5.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btShow1,
            R.id.btShow2,
            R.id.btShow3,
            R.id.btShow4,
            R.id.btShow5 -> LPopupMenu.show(activity, v, R.menu.menu_popup, object : CallbackPopup {
                override fun clickOnItem(menuItem: MenuItem) {
                    LToast.show(activity, menuItem.title.toString())
                }
            })
        }
    }
}
