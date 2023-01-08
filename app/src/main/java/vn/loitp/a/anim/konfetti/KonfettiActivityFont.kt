package vn.loitp.a.anim.konfetti

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_konfetti.*
import vn.loitp.R

@LogTag("KonfettiActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class KonfettiActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_konfetti
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
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
            this.tvTitle?.text = KonfettiActivityFont::class.java.simpleName
        }

        btnFestive.setSafeOnClickListener {
            konfettiView.start(Presets.festive())
        }
        btnExplode.setSafeOnClickListener {
            konfettiView.start(Presets.explode())
        }
        btnParade.setSafeOnClickListener {
            konfettiView.start(Presets.parade())
        }
        btnRain.setSafeOnClickListener {
            konfettiView.start(Presets.rain())
        }
    }
}
