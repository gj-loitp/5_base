package vn.loitp.up.a.demo.ad.adaptiveBanner

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAdmobAdaptiveBannerBinding

@LogTag("AdaptiveBannerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class AdaptiveBannerActivity : BaseActivityFont() {

    private lateinit var binding: AAdmobAdaptiveBannerBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAdmobAdaptiveBannerBinding.inflate(layoutInflater)
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = AdaptiveBannerActivity::class.java.simpleName
        }

        setupAdmob()
    }

    private fun setupAdmob() {

    }
}
