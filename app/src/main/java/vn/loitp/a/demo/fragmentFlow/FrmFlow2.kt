package vn.loitp.a.demo.fragmentFlow

import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import kotlinx.android.synthetic.main.f_demo_flow_2.*
import vn.loitp.R

@LogTag("FrmFlow2")
class FrmFlow2 : BaseFragmentFlow() {

    override fun setLayoutResourceId(): Int {
        return R.layout.f_demo_flow_2
    }

    override fun onBackPressed(): Boolean {
        print("onBackClick")
        popThisFragment()
        return true
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        print("onViewCreated")
        setupViews()
    }

    private fun setupViews() {
        bt.setOnClickListener {
            popThisFragment()
        }
    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        print("onFragmentResume")
    }

    private fun print(msg: String) {
        (activity as FragmentFlowActivityFont).print("FrmFlow2: $msg")
    }
}
