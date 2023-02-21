package vn.loitp.up.a.cv.layout.draggablePanelFree

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.ADraggablePanelFreeBinding

@LogTag("DraggablePanelFreeActivity")
@IsFullScreen(false)
class DraggablePanelFreeActivity : BaseActivityFont() {
    private lateinit var binding: ADraggablePanelFreeBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADraggablePanelFreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = DraggablePanelFreeActivity::class.java.simpleName
        }
        binding.btMaximize.setSafeOnClickListener {
            binding.dpfl.maximize()
        }
        binding.btMinimize.setSafeOnClickListener {
            binding.dpfl.minimize()
        }
        binding.dpfl.setCallback { state ->
            binding.tvState.text = "onStateChange " + state.name
        }
    }
}
