package vn.loitp.app.activity.demo.fragmentFlow

import com.loitpcore.core.base.BaseFragment
import com.loitpcore.core.base.OnBackPressedListener

abstract class BaseFragmentFlow : BaseFragment(), OnBackPressedListener {

    override fun onDestroyView() {
        super.onDestroyView()
        if (activity is FragmentFlowActivity) {
            (activity as FragmentFlowActivity).onBackClickListener = null
        }
    }

    open fun popThisFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    // this function will be called when backstack
    open fun onFragmentResume() {
        if (activity is FragmentFlowActivity) {
            (activity as FragmentFlowActivity).onBackClickListener = this
        }
    }
}
