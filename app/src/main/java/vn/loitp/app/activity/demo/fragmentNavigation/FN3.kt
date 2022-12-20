package vn.loitp.app.activity.demo.fragmentNavigation

import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.base.OnBackPressedListener
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
        setupViews()
    }

    private fun setupViews() {
        button3.setOnClickListener {
            fragmentNavigationActivity?.navController?.navigate(R.id.action_fn3_to_fn1)
        }
    }

    override fun onResume() {
        super.onResume()
        logD("onResume FN3")
    }

    override fun onBackPressed(): Boolean {
        logD("onBackPressed FN3")
        fragmentNavigationActivity?.popThisFragment()
        return true
    }
}
