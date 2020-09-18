package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import com.annotation.LayoutId
import com.core.base.BaseFragment

import vn.loitp.app.R

@LayoutId(R.layout.frm_coordinator_2)
class FrmCoordinator2 : BaseFragment() {

    override fun setTag(): String? {
        return javaClass.simpleName
    }
}
