package vn.loitp.app.activity.demo.fragmentnavigation

import android.os.Bundle
import android.view.View
import android.widget.Button

import com.core.base.BaseFragment
import com.core.utilities.LLog

import vn.loitp.app.R


class FN3 : BaseFragment(), OnBackPressedListener {
    override fun setTag(): String? {
        return javaClass.simpleName
    }

    private var fragmentNavigationActivity: FragmentNavigationActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentNavigationActivity = activity as FragmentNavigationActivity?
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.fn_3
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val button = view.findViewById<Button>(R.id.button3)
        button.setOnClickListener { fragmentNavigationActivity!!.navController.navigate(R.id.action_fn3_to_fn1) }

    }

    override fun onResume() {
        super.onResume()
        LLog.d(fragmentNavigationActivity!!.T, "onResume FN3")
    }

    override fun onBackPressed() {
        LLog.d(fragmentNavigationActivity!!.T, "onBackPressed FN3")
        fragmentNavigationActivity!!.popThisFragment()
    }
}
