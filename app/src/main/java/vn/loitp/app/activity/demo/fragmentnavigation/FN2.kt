package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.utilities.LLog
import kotlinx.android.synthetic.main.frm_fn_2.*
import vn.loitp.app.R

@LayoutId(R.layout.frm_fn_2)
@LogTag("FN2")
class FN2 : BaseFragment(), OnBackPressedListener {

    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button2.setOnClickListener {
            val navController = fragmentNavigationActivity?.navController
            //new NavOptions.Builder().setExitAnim(R.anim.fade_out);
            navController?.navigate(R.id.action_fn2_to_fn3)
        }

    }

    override fun onResume() {
        super.onResume()
        LLog.d(fragmentNavigationActivity!!.T, "onResume FN2")
    }

    override fun onBackPressed() {
        LLog.d(fragmentNavigationActivity!!.T, "onBackPressed FN2")
        fragmentNavigationActivity?.popThisFragment()
    }
}
