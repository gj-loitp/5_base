package vn.loitp.up.a.cv.rv.arcView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.amir.arcview.ArcLinearLayout
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ARvArcViewBinding

@LogTag("ArcViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class ArcViewActivity : BaseActivityFont(), View.OnClickListener {
    private lateinit var strokeArc: ArcLinearLayout
    private lateinit var shadowArc: ArcLinearLayout
    private lateinit var binding: ARvArcViewBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARvArcViewBinding.inflate(layoutInflater)
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
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/amir5121/arcView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ArcViewActivity::class.java.simpleName
        }

        binding.btKickMe.setOnClickListener(this)
        binding.btKickSwapped.setOnClickListener(this)
        binding.layoutIncludeArcButton.includeButtonsStroke.setOnClickListener(this)
        binding.layoutIncludeArcButton.includeButtonsShadow.setOnClickListener(this)
        strokeArc = layoutInflater.inflate(
            R.layout.stroke_arc_linear_layout,
            binding.layoutIncludeArcButton.includeArcButtonsTempArc,
            false
        ) as ArcLinearLayout
        shadowArc = layoutInflater.inflate(
            R.layout.shadow_arc_linear_layout,
            binding.layoutIncludeArcButton.includeArcButtonsTempArc,
            false
        ) as ArcLinearLayout
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btKickMe -> {
                if (binding.layoutIncludeArcButton.includeButtonsScrollView.isKnockedIn) {
                    binding.layoutIncludeArcButton.includeButtonsScrollView.knockout()
                } else {
                    binding.layoutIncludeArcButton.includeButtonsScrollView.knockIn()
                }
            }
            binding.btKickSwapped -> {
                binding.layoutIncludeArcButton.includeArcButtonsTempArc.swapView(null)
            }
            binding.layoutIncludeArcButton.includeButtonsShadow -> {
                binding.layoutIncludeArcButton.includeArcButtonsTempArc.swapView(shadowArc)
            }
            binding.layoutIncludeArcButton.includeButtonsStroke -> {
                binding.layoutIncludeArcButton.includeArcButtonsTempArc.swapView(strokeArc)
            }
        }
    }
}
