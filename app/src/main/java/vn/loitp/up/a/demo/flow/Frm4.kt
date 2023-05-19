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
import kotlinx.android.synthetic.main.f_flow_4.*
import kotlinx.coroutines.launch
import vn.loitp.R

@LogTag("loitppFrm4")
class Frm4 : BaseFragment() {
    private var viewModel: FlowViewModel? = null
    override fun setLayoutResourceId(): Int {
        return R.layout.f_flow_4
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
        btGoNext.setOnClickListener {
            activity?.addFragment(R.id.fl1, Frm5(), true)
        }
        btPop.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
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
