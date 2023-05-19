package vn.loitp.up.a.demo.flow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import kotlinx.android.synthetic.main.f_flow_4.*
import kotlinx.android.synthetic.main.f_flow_5.btEmit
import kotlinx.android.synthetic.main.f_flow_5.btPop
import kotlinx.coroutines.launch
import vn.loitp.R

@LogTag("loitppFrm5")
class Frm5 : BaseFragment() {
    private var viewModel: FlowViewModel? = null
    override fun setLayoutResourceId(): Int {
        return R.layout.f_flow_5
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModels()
    }

    private fun setupView() {
        btPop.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        btEmit.setOnClickListener {
            viewModel?.updateTimeStateWithDefaultValue()
            viewModel?.updateTimeStateNoDefaultValue()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModels() {
        viewModel = getViewModel(FlowViewModel::class.java)
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.timeStateWithDefaultValue?.collect { value ->
                    logE(">>>>>>~~~~value timeState $value")
                    tvTime.text = value
                }
            }
        }
    }

}
