package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFragment
import com.core.utilities.LLog
import vn.loitp.app.R

@LayoutId(R.layout.frm_fn_4)
class FN4 : BaseFragment(), OnBackPressedListener {
    override fun setTag(): String? {
        return javaClass.simpleName
    }

    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

    override fun onResume() {
        super.onResume()
        LLog.d(fragmentNavigationActivity!!.T, "onResume FN4")
    }

    override fun onBackPressed() {
        LLog.d(fragmentNavigationActivity!!.T, "onBackPressed FN4")
        fragmentNavigationActivity?.popThisFragment()
    }
}
