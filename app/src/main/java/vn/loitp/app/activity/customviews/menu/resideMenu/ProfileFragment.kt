package vn.loitp.app.activity.customviews.menu.resideMenu

import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import vn.loitp.app.R

@LogTag("ProfileFragment")
class ProfileFragment : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_reside_menu_profile
    }
}
