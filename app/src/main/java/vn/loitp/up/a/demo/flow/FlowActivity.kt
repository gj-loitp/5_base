package vn.loitp.up.a.demo.flow

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.addFragment
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.A0Binding
import vn.loitp.databinding.AFlowBinding

@LogTag("FlowActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FlowActivity : BaseActivityFont() {

    private lateinit var binding: AFlowBinding
    private val frm2 = Frm2()
    private val frm3 = Frm3()

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                isVisible = false
            }
            this.tvTitle?.text = FlowActivity::class.java.simpleName
        }

        addFragment(R.id.fl2, frm2, false)
        addFragment(R.id.fl3, frm3, false)
    }
}
