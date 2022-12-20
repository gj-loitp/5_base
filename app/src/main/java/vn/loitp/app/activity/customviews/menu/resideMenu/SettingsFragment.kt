package vn.loitp.app.activity.customviews.menu.resideMenu

import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import vn.loitp.app.R

@LogTag("SettingsFragment")
class SettingsFragment : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_reside_menu_setting
    }
}
