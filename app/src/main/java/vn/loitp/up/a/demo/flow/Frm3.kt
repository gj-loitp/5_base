package vn.loitp.up.a.demo.flow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import kotlinx.android.synthetic.main.f_flow_3.btClick
import kotlinx.android.synthetic.main.f_flow_3.tv
import vn.loitp.R

@LogTag("loitppFrm3")
class Frm3 : BaseFragment() {
    private var viewModel: FlowViewModel? = null
    override fun setLayoutResourceId(): Int {
        return R.layout.f_flow_3
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
        btClick.setOnClickListener {
            viewModel?.setName(System.currentTimeMillis().toString())
        }
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
