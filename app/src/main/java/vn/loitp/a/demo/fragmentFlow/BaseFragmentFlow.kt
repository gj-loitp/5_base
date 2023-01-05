package vn.loitp.a.demo.fragmentFlow

import com.loitp.core.base.BaseFragment
import com.loitp.core.base.OnBackPressedListener

abstract class BaseFragmentFlow : BaseFragment(), OnBackPressedListener {

    override fun onDestroyView() {
        super.onDestroyView()
        if (activity is FragmentFlowActivityFont) {
            (activity as FragmentFlowActivityFont).onBackClickListener = null
        }
    }

    open fun popThisFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    // this function will be called when backstack
    open fun onFragmentResume() {
        if (activity is FragmentFlowActivityFont) {
            (activity as FragmentFlowActivityFont).onBackClickListener = this
        }
    }
}
