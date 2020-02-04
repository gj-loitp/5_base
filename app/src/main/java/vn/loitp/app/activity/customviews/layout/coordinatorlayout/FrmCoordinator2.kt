package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import com.core.base.BaseFragment

import vn.loitp.app.R

class FrmCoordinator2 : BaseFragment() {
    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coordinator_2
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }
}
