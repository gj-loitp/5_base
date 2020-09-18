package vn.loitp.app.activity.demo.fragmentflow

import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import kotlinx.android.synthetic.main.frm_demo_flow_0.*
import vn.loitp.app.R

@LayoutId(R.layout.frm_demo_flow_0)
@LogTag("FrmFlow0")
class FrmFlow0 : FrmFlowBase() {
    override fun onBackClick(): Boolean {
        print("onBackClick")
        popThisFragment()
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        print("onViewCreated")
        bt.setOnClickListener {
            (activity as FragmentFlowActivity).showFragment(FrmFlow1())
        }
    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        print("onFragmentResume")
    }

    private fun print(msg: String) {
        (activity as FragmentFlowActivity).print("FrmFlow0: $msg")
    }
}
