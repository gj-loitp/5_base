package vn.loitp.app.activity.customviews.menu.resideMenu

import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import vn.loitp.R

@LogTag("SettingsFragment")
class SettingsFragment : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_reside_menu_setting
    }
}
