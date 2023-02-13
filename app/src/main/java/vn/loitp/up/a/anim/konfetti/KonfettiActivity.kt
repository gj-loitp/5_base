package vn.loitp.up.a.anim.konfetti

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AKonfettiBinding

@LogTag("KonfettiActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class KonfettiActivity : BaseActivityFont() {
    private lateinit var binding: AKonfettiBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AKonfettiBinding.inflate(layoutInflater)
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
                            url = "https://github.com/DanielMartinus/konfetti"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = KonfettiActivity::class.java.simpleName
        }

        binding.btnFestive.setSafeOnClickListener {
            binding.konfettiView.start(Presets.festive())
        }
        binding.btnExplode.setSafeOnClickListener {
            binding.konfettiView.start(Presets.explode())
        }
        binding.btnParade.setSafeOnClickListener {
            binding.konfettiView.start(Presets.parade())
        }
        binding.btnRain.setSafeOnClickListener {
            binding.konfettiView.start(Presets.rain())
        }
    }
}
