package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View
import android.widget.Button

import com.core.base.BaseFragment
import com.core.utilities.LLog

import loitp.basemaster.R

class FN1 : BaseFragment(), OnBackPressedListener {
    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.fn_1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener { v -> fragmentNavigationActivity!!.navController.navigate(R.id.action_fn1_to_fn2) }
        view.findViewById<View>(R.id.button_4).setOnClickListener { v -> fragmentNavigationActivity!!.navController.navigate(R.id.action_fn1_to_fn4) }
    }

    override fun onResume() {
        super.onResume()
        LLog.d(fragmentNavigationActivity!!.T, "onResume FN1")
    }

    override fun onBackPressed() {
        LLog.d(fragmentNavigationActivity!!.T, "onBackPressed FN1")
        fragmentNavigationActivity!!.popThisFragment()
    }
}