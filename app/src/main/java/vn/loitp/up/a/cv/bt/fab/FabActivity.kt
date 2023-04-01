package vn.loitp.up.a.cv.bt.fab

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AFabBinding

@LogTag("FabActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class FabActivity : BaseActivityFont() {
    private lateinit var binding: AFabBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFabBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = FabActivity::class.java.simpleName
        }
        binding.btAiNone.setSafeOnClickListener {
            binding.btAiNone.shrink(object : ExtendedFloatingActionButton.OnChangedCallback() {
                override fun onShrunken(extendedFab: ExtendedFloatingActionButton?) {
                    super.onShrunken(extendedFab)
                }

                override fun onExtended(extendedFab: ExtendedFloatingActionButton?) {
                    super.onExtended(extendedFab)
                }
            })
        }
        binding.btAiNone2.shrink()
        binding.btAiNone2.setSafeOnClickListener {
            binding.btAiNone2.extend()
        }
    }
}
