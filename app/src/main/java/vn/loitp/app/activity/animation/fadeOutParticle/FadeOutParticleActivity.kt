package vn.loitp.app.activity.animation.fadeOutParticle

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.*
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_fade_out_particle.*
import vn.loitp.app.R

@LogTag("FadeOutParticleActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FadeOutParticleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fade_out_particle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/hoomanmmd/FadeOutParticle"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FadeOutParticleActivity::class.java.simpleName
        }

        btStart.setOnClickListener {
            fadeOutParticle.startAnimation()
        }

        btReset.setOnClickListener {
            fadeOutParticle.reset()
        }
    }
}
