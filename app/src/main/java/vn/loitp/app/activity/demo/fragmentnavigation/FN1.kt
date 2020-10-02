package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_fn_1.*
import vn.loitp.app.R

@LayoutId(R.layout.frm_fn_1)
@LogTag("fragmentNavigationActivity")
class FN1 : BaseFragment(), OnBackPressedListener {

    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button.setOnClickListener {
            fragmentNavigationActivity?.navController?.navigate(R.id.action_fn1_to_fn2)
        }
        view.findViewById<View>(R.id.button4).setOnClickListener { fragmentNavigationActivity?.navController?.navigate(R.id.action_fn1_to_fn4) }
    }

    override fun onResume() {
        super.onResume()
        logD("onResume FN1")
    }

    override fun onBackPressed() {
        logD("onBackPressed FN1")
        fragmentNavigationActivity?.popThisFragment()
    }
}
