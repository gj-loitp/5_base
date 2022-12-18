package vn.loitp.app.activity.demo.fragmentNavigation

import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_fn_2.*
import vn.loitp.app.R

@LogTag("fragmentNavigationActivity")
class FN2 : BaseFragment(), OnBackPressedListener {

    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_fn_2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
    }

    private fun setupViews() {
        button2.setOnClickListener {
            val navController = fragmentNavigationActivity?.navController
            // new NavOptions.Builder().setExitAnim(R.anim.fade_out);
            navController?.navigate(R.id.action_fn2_to_fn3)
        }
    }

    override fun onResume() {
        super.onResume()
        logD("onResume FN2")
    }

    override fun onBackPressed(): Boolean {
        logD("onBackPressed FN2")
        fragmentNavigationActivity?.popThisFragment()
        return true
    }
}
