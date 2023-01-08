package vn.loitp.a.interviewVN

import com.loitp.core.base.BaseFragment
import com.loitp.core.base.OnBackPressedListener

abstract class BaseFragmentFlow : BaseFragment(), OnBackPressedListener {

    override fun onDestroyView() {
        super.onDestroyView()
        if (activity is InterviewVNIQActivityFont) {
            (activity as InterviewVNIQActivityFont).onBackClickListener = null
        }
    }

    open fun popThisFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    // this function will be called when backstack
    open fun onFragmentResume() {
        if (activity is InterviewVNIQActivityFont) {
            (activity as InterviewVNIQActivityFont).onBackClickListener = this
        }
    }
}
