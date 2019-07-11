package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View

import com.core.base.BaseFragment
import com.core.utilities.LLog

import loitp.basemaster.R


class FN4 : BaseFragment(), OnBackPressedListener {
    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.fn_4
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

    override fun onResume() {
        super.onResume()
        LLog.d(fragmentNavigationActivity!!.T, "onResume FN4")
    }

    override fun onBackPressed() {
        LLog.d(fragmentNavigationActivity!!.T, "onBackPressed FN4")
        fragmentNavigationActivity!!.popThisFragment()
    }
}
