package vn.loitp.up.a.demo.flow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.addFragment
import kotlinx.android.synthetic.main.f_flow_1.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import vn.loitp.R

@LogTag("loitppFrm1")
class Frm1 : BaseFragment() {
    private var viewModel: FlowViewModel? = null
    override fun setLayoutResourceId(): Int {
        return R.layout.f_flow_1
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
        btGoNext.setOnClickListener {
            activity?.addFragment(R.id.fl1, Frm4(), true)
        }
    }

    private fun setupViewModels() {
        viewModel = getViewModel(FlowViewModel::class.java)
        viewModel?.let { vm ->
            vm.nameLiveEvent.observe(viewLifecycleOwner) { name ->
//                logD(">>>name $logTag $name")
                tv.text = name
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.countState?.collect { value ->
//                    logE(">>>>>>~~~~value $value")
                    tvCount.text = "$value"
                }
                viewModel?.timeState?.collect { value ->
                    logE(">>>>>>~~~~value timeState $value")
                    tvTime.text = value
                }
            }
        }
    }
}
