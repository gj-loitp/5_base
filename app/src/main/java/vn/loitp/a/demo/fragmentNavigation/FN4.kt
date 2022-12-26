package vn.loitp.a.demo.fragmentNavigation

import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.base.OnBackPressedListener
import vn.loitp.R

@LogTag("fragmentNavigationActivity")
class FN4 : BaseFragment(), OnBackPressedListener {

    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.f_fn_4
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
