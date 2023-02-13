package vn.loitp.up.a.demo.fragmentFlow

import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import kotlinx.android.synthetic.main.f_demo_flow_0.*
import vn.loitp.R

@LogTag("FrmFlow0")
class FrmFlow0 : BaseFragmentFlow() {

    override fun setLayoutResourceId(): Int {
        return R.layout.f_demo_flow_0
    }

    override fun onBackPressed(): Boolean {
        print("onBackClick")
        popThisFragment()
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        print("onViewCreated")
        setupViews()
    }

    private fun setupViews() {
        bt.setOnClickListener {
            if (activity is FragmentFlowActivity) {
                (activity as FragmentFlowActivity).showFragment(FrmFlow1())
            }
        }
    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        print("onFragmentResume")
    }

    private fun print(msg: String) {
        if (activity is FragmentFlowActivity) {
            (activity as FragmentFlowActivity).print("FrmFlow0: $msg")
        }
    }
}
