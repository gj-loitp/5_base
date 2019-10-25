package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import com.core.base.BaseFragment

import loitp.basemaster.R

class FrmCoordinator2 : BaseFragment() {
    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coordinator_2
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }
}
