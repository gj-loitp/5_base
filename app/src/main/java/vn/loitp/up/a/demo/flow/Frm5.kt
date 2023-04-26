package vn.loitp.up.a.demo.flow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.addFragment
import kotlinx.android.synthetic.main.f_flow_4.*
import kotlinx.android.synthetic.main.f_flow_5.btPop
import vn.loitp.R

@LogTag("loitppFrm5")
class Frm5 : BaseFragment() {
    override fun setLayoutResourceId(): Int {
        return R.layout.f_flow_5
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        btPop.setOnClickListener {

        }
    }

}
