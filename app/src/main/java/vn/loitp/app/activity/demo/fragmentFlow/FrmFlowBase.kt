package vn.loitp.app.activity.demo.fragmentFlow

import com.loitpcore.core.base.BaseFragment

abstract class FrmFlowBase : BaseFragment(), OnBackPressedListener {

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as FragmentFlowActivity).onBackClickListener = null
    }

    open fun popThisFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    // this function will be called when backstack
    open fun onFragmentResume() {
        (activity as FragmentFlowActivity).onBackClickListener = this
    }
}
