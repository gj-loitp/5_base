package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFragment
import com.core.utilities.LLog
import kotlinx.android.synthetic.main.frm_fn_3.*
import vn.loitp.app.R

@LayoutId(R.layout.frm_fn_3)
class FN3 : BaseFragment(), OnBackPressedListener {
    override fun setTag(): String? {
        return javaClass.simpleName
    }

    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

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
        LLog.d(fragmentNavigationActivity!!.T, "onResume FN3")
    }

    override fun onBackPressed() {
        LLog.d(fragmentNavigationActivity!!.T, "onBackPressed FN3")
        fragmentNavigationActivity?.popThisFragment()
    }
}
