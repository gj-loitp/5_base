package vn.loitp.up.a.cv.iv.roundedIv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.common.URL_IMG_1
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AIvRoundedBinding

@LogTag("RoundedIvActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class RoundedIvActivity : BaseActivityFont() {
    private lateinit var binding: AIvRoundedBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AIvRoundedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

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
                            url = "https://github.com/rishabh876/RoundedImageView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = RoundedIvActivity::class.java.simpleName
        }
        binding.ivCircle.loadGlide(
            any = URL_IMG_1,
        )
        binding.iv.loadGlide(
            any = URL_IMG_1,
        )
        binding.iv1.loadGlide(
            any = URL_IMG_1,
        )
        binding.iv2.loadGlide(
            any = URL_IMG_1,
        )
        binding.iv4.loadGlide(
            any = URL_IMG_1,
        )
        binding.iv5.loadGlide(
            any = URL_IMG_1,
        )
    }
}
