package vn.loitp.app.activity.demo.fragmentNavigation

import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import vn.loitp.app.R

@LogTag("fragmentNavigationActivity")
class FN4 : BaseFragment(), OnBackPressedListener {

    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_fn_4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

    override fun onResume() {
        super.onResume()
        logD("onResume FN4")
    }

    override fun onBackPressed(): Boolean {
        logD("onBackPressed FN4")
        fragmentNavigationActivity?.popThisFragment()
        return true
    }
}
