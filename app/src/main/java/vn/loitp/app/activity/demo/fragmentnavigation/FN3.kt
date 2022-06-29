package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import kotlinx.android.synthetic.main.frm_fn_3.*
import vn.loitp.app.R

@LogTag("fragmentNavigationActivity")
class FN3 : BaseFragment(), OnBackPressedListener {

    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_fn_3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button3.setOnClickListener {
            fragmentNavigationActivity?.navController?.navigate(R.id.action_fn3_to_fn1)
        }
    }

    override fun onResume() {
        super.onResume()
        logD("onResume FN3")
    }

    override fun onBackPressed() {
        logD("onBackPressed FN3")
        fragmentNavigationActivity?.popThisFragment()
    }
}
