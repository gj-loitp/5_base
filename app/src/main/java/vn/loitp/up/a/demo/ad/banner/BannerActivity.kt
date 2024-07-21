package vn.loitp.up.a.demo.ad.banner

import android.os.Bundle
import com.applovin.mediation.ads.MaxAdView
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAdBannerBinding
import vn.loitp.up.a.demo.ad.createAdBanner
import vn.loitp.up.a.demo.ad.destroyAdBanner

@LogTag("BannerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class BannerActivity : BaseActivityFont() {

    private lateinit var binding: AAdBannerBinding
    private var adView: MaxAdView? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAdBannerBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = BannerActivity::class.java.simpleName
        }

        adView = this@BannerActivity.createAdBanner(
            logTag = logTag,
            bkgColor = getColor(R.color.colorPrimary),
            viewGroup = binding.flAd,
            isAdaptiveBanner = true,
        )
    }

    override fun onDestroy() {
        binding.flAd.destroyAdBanner(adView)
        super.onDestroy()
    }

}
