package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFragment
import vn.loitp.app.R

@LayoutId(R.layout.frm_fn_4)
@LogTag("fragmentNavigationActivity")
class FN4 : BaseFragment(), OnBackPressedListener {

    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

    override fun onResume() {
        super.onResume()
        logD("onResume FN4")
    }

    override fun onBackPressed() {
        logD("onBackPressed FN4")
        fragmentNavigationActivity?.popThisFragment()
    }
}
