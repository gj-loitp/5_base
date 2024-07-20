package vn.loitp.up.a.anim.fadeOutParticle

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AFadeOutParticleBinding

@LogTag("FadeOutParticleActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FadeOutParticleActivity : BaseActivityFont() {

    private lateinit var binding: AFadeOutParticleBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFadeOutParticleBinding.inflate(layoutInflater)
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
                            url = "https://github.com/hoomanmmd/FadeOutParticle"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FadeOutParticleActivity::class.java.simpleName
        }

        binding.btStart.setOnClickListener {
            binding.fadeOutParticle.startAnimation()
        }

        binding.btReset.setOnClickListener {
            binding.fadeOutParticle.reset()
        }
    }
}
