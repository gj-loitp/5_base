package vn.loitp.app.activity.demo.fragmentflow

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.frm_flow_2.*
import loitp.basemaster.R

class FrmFlow2 : FrmFlowBase() {
    override fun onBackClick(): Boolean {
        print("onBackClick")
        popThisFragment()
        return true
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_flow_2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        print("onViewCreated")
        bt.setOnClickListener {
            popThisFragment()
        }
    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        print("onFragmentResume")
    }

    fun print(msg: String) {
        (activity as FragmentFlowActivity).print("FrmFlow2: $msg")
    }
}
