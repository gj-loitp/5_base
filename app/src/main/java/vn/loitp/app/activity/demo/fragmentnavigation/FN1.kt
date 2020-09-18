package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View
import com.annotation.LayoutId

import com.core.base.BaseFragment
import com.core.utilities.LLog
import kotlinx.android.synthetic.main.frm_fn_1.*

import vn.loitp.app.R

@LayoutId(R.layout.frm_fn_1)
class FN1 : BaseFragment(), OnBackPressedListener {
    override fun setTag(): String? {
        return javaClass.simpleName
    }

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
        LLog.d(fragmentNavigationActivity!!.T, "onResume FN1")
    }

    override fun onBackPressed() {
        LLog.d(fragmentNavigationActivity!!.T, "onBackPressed FN1")
        fragmentNavigationActivity?.popThisFragment()
    }
}