package vn.loitp.up.a.demo.flow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import kotlinx.android.synthetic.main.f_bottom.*
import kotlinx.android.synthetic.main.f_flow_2.tv
import vn.loitp.R

@LogTag("Frm2")
class Frm2 : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.f_flow_2
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        tv.text = "Hello"
    }
}
