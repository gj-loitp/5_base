package vn.loitp.up.a.demo.fragmentFlow

import com.loitp.core.base.BaseFragment
import com.loitp.core.base.OnBackPressedListener

abstract class BaseFragmentFlow : BaseFragment(), OnBackPressedListener {

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as FragmentFlowActivity?)?.onBackClickListener = null
    }

    open fun popThisFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    // this function will be called when backstack
    open fun onFragmentResume() {
        (activity as FragmentFlowActivity?)?.onBackClickListener = this
    }
}
