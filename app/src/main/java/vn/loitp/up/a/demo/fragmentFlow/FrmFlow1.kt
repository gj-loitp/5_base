package vn.loitp.up.a.demo.fragmentFlow

import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import kotlinx.android.synthetic.main.f_demo_flow_1.*
import vn.loitp.R

@LogTag("FrmFlow1")
class FrmFlow1 : BaseFragmentFlow() {

    override fun setLayoutResourceId(): Int {
        return R.layout.f_demo_flow_1
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
            (activity as FragmentFlowActivity).showFragment(FrmFlow2())
        }
    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        print("onFragmentResume")
    }

    fun print(msg: String) {
        (activity as FragmentFlowActivity).print("FrmFlow1: $msg")
    }
}
