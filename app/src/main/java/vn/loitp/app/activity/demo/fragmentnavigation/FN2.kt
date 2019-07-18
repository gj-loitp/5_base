package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.core.base.BaseFragment
import com.core.utilities.LLog
import loitp.basemaster.R

class FN2 : BaseFragment(), OnBackPressedListener {
    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.fn_2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val button = view.findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            val navController = fragmentNavigationActivity!!.navController
            //new NavOptions.Builder().setExitAnim(R.anim.fade_out);
            navController.navigate(R.id.action_fn2_to_fn3)
        }

    }

    override fun onResume() {
        super.onResume()
        LLog.d(fragmentNavigationActivity!!.T, "onResume FN2")
    }

    override fun onBackPressed() {
        LLog.d(fragmentNavigationActivity!!.T, "onBackPressed FN2")
        fragmentNavigationActivity!!.popThisFragment()
    }
}
