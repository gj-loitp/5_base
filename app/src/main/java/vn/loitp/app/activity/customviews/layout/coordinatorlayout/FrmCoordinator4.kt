package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import android.os.Bundle
import android.view.View

import com.core.base.BaseFragment

import vn.loitp.app.R

class FrmCoordinator4 : BaseFragment() {
    override fun setLayoutResourceId(): Int {
        return R.layout.frm_coordinator_4
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }
}
