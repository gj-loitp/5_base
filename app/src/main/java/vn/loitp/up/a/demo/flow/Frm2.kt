package vn.loitp.up.a.demo.flow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import kotlinx.android.synthetic.main.f_flow_2.*
import kotlinx.coroutines.launch
import vn.loitp.R

@LogTag("Frm2")
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
    }

    private fun setupViewModels() {
        viewModel = getViewModel(FlowViewModel::class.java)
        viewModel?.let { vm ->
            vm.nameLiveEvent.observe(viewLifecycleOwner) { name ->
                logD(">>>name $logTag $name")
                tv.text = name
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.countState?.collect { value ->
                    tvCount.text = "$value"
                }
            }
        }
    }
}
