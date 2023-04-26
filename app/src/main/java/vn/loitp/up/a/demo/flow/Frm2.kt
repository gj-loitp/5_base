package vn.loitp.up.a.demo.flow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import kotlinx.android.synthetic.main.f_bottom.*
import kotlinx.android.synthetic.main.f_flow_2.tv
import vn.loitp.R

@LogTag("loitppFrm2")
class Frm2 : BaseFragment() {
    private var viewModel: FlowViewModel? = null
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
        setupViewModels()
    }

    private fun setupView() {
        tv.text = "Hello"
    }

    private fun setupViewModels() {
        viewModel = getViewModel(FlowViewModel::class.java)
        viewModel?.let { vm ->
            vm.nameLiveEvent.observe(viewLifecycleOwner) { name ->
                logD(">>>name $logTag $name")
                tv.text = name
            }
        }
    }
}
