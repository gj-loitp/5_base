package vn.loitp.app.activity.customviews.popupmenu

import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LPopupMenu
import kotlinx.android.synthetic.main.activity_menu_popup.*
import vn.loitp.app.R

@LogTag("PopupMenuActivity")
@IsFullScreen(false)
class PopupMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_popup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btShow1.setOnClickListener(this)
        btShow2.setOnClickListener(this)
        btShow3.setOnClickListener(this)
        btShow4.setOnClickListener(this)
        btShow5.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btShow1,
            btShow2,
            btShow3,
            btShow4,
            btShow5 -> {
                LPopupMenu.show(
                    activity = this,
                    showOnView = v,
                    menuRes = R.menu.menu_popup,
                    callback = { menuItem ->
                        showShortInformation(menuItem.title.toString())
                    }
                )
            }
        }
    }
}
