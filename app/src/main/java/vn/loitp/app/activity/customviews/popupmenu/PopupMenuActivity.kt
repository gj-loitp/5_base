package vn.loitp.app.activity.customviews.popupmenu

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LPopupMenu
import com.interfaces.CallbackPopup
import kotlinx.android.synthetic.main.activity_menu_popup.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_menu_popup)
@LogTag("PopupMenuActivity")
@IsFullScreen(false)
class PopupMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btShow1.setOnClickListener(this)
        btShow2.setOnClickListener(this)
        btShow3.setOnClickListener(this)
        btShow4.setOnClickListener(this)
        btShow5.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btShow1,
            R.id.btShow2,
            R.id.btShow3,
            R.id.btShow4,
            R.id.btShow5 -> LPopupMenu.show(activity = this,
                    showOnView = v,
                    menuRes = R.menu.menu_popup,
                    callBackPopup = object : CallbackPopup {
                        override fun clickOnItem(menuItem: MenuItem) {
                            showShort(menuItem.title.toString())
                        }
                    })
        }
    }
}
