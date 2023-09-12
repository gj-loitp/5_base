package vn.loitp.up.a.anim.androidParticles

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AAndroidParticlesBinding

@LogTag("AndroidParticlesActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class AndroidParticlesActivity : BaseActivityFont() {
    private lateinit var binding: AAndroidParticlesBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAndroidParticlesBinding.inflate(layoutInflater)
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
                            url = "https://github.com/ibrahimsn98/android-particles"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = AndroidParticlesActivity::class.java.simpleName
        }
    }

    override fun onResume() {
        super.onResume()
        binding.particleView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.particleView.pause()
    }
}
