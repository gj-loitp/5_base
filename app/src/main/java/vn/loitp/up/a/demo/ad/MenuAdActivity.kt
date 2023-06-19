package vn.loitp.up.a.demo.ad

import android.os.Bundle
import com.applovin.sdk.AppLovinSdk
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.BuildConfig
import vn.loitp.R
import vn.loitp.databinding.AMenuAdBinding
import vn.loitp.up.a.demo.ad.banner.BannerActivity
import vn.loitp.up.a.demo.ad.interstitial.InterstitialActivity

@LogTag("MenuAdActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuAdActivity : BaseActivityFont() {

    private lateinit var binding: AMenuAdBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuAdBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuAdActivity::class.java.simpleName
        }
        binding.btBanner.setSafeOnClickListener {
            launchActivity(BannerActivity::class.java)
        }
        binding.btInterstitial.setSafeOnClickListener {
            launchActivity(InterstitialActivity::class.java)
        }
        binding.btShowMediationDebugger.setSafeOnClickListener {
            if (BuildConfig.DEBUG) {
                AppLovinSdk.getInstance(this).showMediationDebugger()
            } else {
                showShortError("Only available in debug mode")
            }
        }

    }

}
