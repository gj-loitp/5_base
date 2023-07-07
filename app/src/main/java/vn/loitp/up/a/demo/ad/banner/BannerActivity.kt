package vn.loitp.up.a.demo.ad.banner

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdRevenueListener
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinSdkUtils
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAdBannerBinding
import vn.loitp.up.a.demo.ad.Applovin

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

        adView = Applovin.createAdBanner(
            a = this,
            logTag = logTag,
            bkgColor = Color.CYAN,
            viewGroup = binding.flAd,
            isAdaptiveBanner = true,
        )
    }

    override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }

}
