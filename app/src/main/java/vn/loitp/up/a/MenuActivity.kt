package vn.loitp.up.a

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsKeepScreenOn
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.*
import com.loitp.core.helper.adHelper.AdHelperActivity
import vn.loitp.R
import vn.loitp.databinding.AMenuBinding
import vn.loitp.up.a.anim.MenuAnimationActivity
import vn.loitp.up.a.api.MenuAPIActivity
import vn.loitp.up.a.cv.MenuCustomViewsActivity
import vn.loitp.up.a.cv3.MenuUI3Activity
import vn.loitp.up.a.db.MenuDatabaseActivity
import vn.loitp.up.a.demo.MenuDemoActivity
import vn.loitp.up.a.demo.ad.createAdBanner
import vn.loitp.up.a.demo.ad.destroyAdBanner
import vn.loitp.up.a.func.MenuFunctionActivity
import vn.loitp.up.a.game.MenuGameActivity
import vn.loitp.up.a.interviewVN.InterviewVNIQActivity
import vn.loitp.up.a.more.MoreActivity
import vn.loitp.up.a.network.MenuNetworkActivity
import vn.loitp.up.a.pattern.MenuPatternActivity
import vn.loitp.up.a.picker.MenuPickerActivity
import vn.loitp.up.a.sec.MenuSecurityActivity
import vn.loitp.up.a.sv.MenuServiceActivity
import vn.loitp.up.a.tut.MenuTutorialActivity
import vn.loitp.up.a.u.UtilsActivity
import vn.loitp.up.a.u.UtilsCoreActivity
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.pow

@LogTag("MenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
@IsKeepScreenOn(true)
class MenuActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: AMenuBinding
    private var adView: MaxAdView? = null
    private var interstitialAd: MaxInterstitialAd? = null
    private var retryAttempt = 0

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        adView = this@MenuActivity.createAdBanner(
            logTag = logTag,
            bkgColor = getColor(R.color.colorPrimary),
            viewGroup = binding.flAd,
            isAdaptiveBanner = true,
        )
        createAdInter()

        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(com.loitp.R.color.transparent)
            this.tvTitle?.text = MenuActivity::class.java.simpleName
        }
        binding.tvPolicy.apply {
            this.setTextUnderline()
            setSafeOnClickListener {
                this@MenuActivity.openBrowserPolicy()
            }
        }
        binding.swDarkTheme.apply {
            isChecked = isDarkTheme()
            setOnCheckedChangeListener { _, isDarkTheme ->
                if (isDarkTheme) {
                    setDarkTheme(isDarkTheme = true)
                } else {
                    setDarkTheme(isDarkTheme = false)
                }
                finish()//correct
                startActivity(Intent(this@MenuActivity, MenuActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }
        binding.btFlutter.setOnClickListener(this)
        binding.btApi.setOnClickListener(this)
        binding.btAnimation.setOnClickListener(this)
        binding.btCustomView.setOnClickListener(this)
        binding.btCustomView3.setOnClickListener(this)
        binding.btDemo.setOnClickListener(this)
        binding.btFunction.setOnClickListener(this)
        binding.btRateApp.setOnClickListener(this)
        binding.btMoreApp.setOnClickListener(this)
        binding.btDatabase.setOnClickListener(this)
        binding.btPattern.setOnClickListener(this)
        binding.btChat.setOnClickListener(this)
        binding.btGithub.setOnClickListener(this)
        binding.btAdHelper.setOnClickListener(this)
        binding.btFbFanpage.setOnClickListener(this)
        binding.btFrmMore.setOnClickListener(this)
        binding.btTutorial.setOnClickListener(this)
        binding.btPicker.setOnClickListener(this)
        binding.btNetwork.setOnClickListener(this)
        binding.btSecurity.setOnClickListener(this)
        binding.btService.setOnClickListener(this)
        binding.btUtils.setOnClickListener(this)
        binding.btUtilsCore.setOnClickListener(this)
        binding.btGame.setOnClickListener(this)
        binding.btFeedback.setOnClickListener(this)
        binding.btInterviewVNIQActivity.setOnClickListener(this)
        binding.tvMoreApp.setOnClickListener(this)
    }

    override fun onDestroy() {
        binding.flAd.destroyAdBanner(adView)
        super.onDestroy()
    }

    private var doubleBackToExitPressedOnce = false

    override fun onBaseBackPressed() {
//        super.onBaseBackPressed()
        if (doubleBackToExitPressedOnce) {
//            onBaseBackPressed()
            super.onBaseBackPressed()//correct
            return
        }
        this.doubleBackToExitPressedOnce = true

//        showSnackBarInfor(msg = getString(com.loitp.R.string.press_again_to_exit), isFullWidth = false)
        showShortInformation(
            msg = getString(com.loitp.R.string.press_again_to_exit),
            isTopAnchor = false,
            drawableT = com.loitp.R.drawable.ic_copyright_black_48dp,
        )

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btFlutter -> rateApp(packageName = "com.roy93group.fullter_tutorial")
            binding.btApi -> {
                showAd()
                launchActivity(MenuAPIActivity::class.java)
            }

            binding.btAnimation -> {
                showAd()
                launchActivity(MenuAnimationActivity::class.java)
            }

            binding.btCustomView -> {
                showAd()
                launchActivity(MenuCustomViewsActivity::class.java)
            }

            binding.btCustomView3 -> {
                showAd()
                launchActivity(MenuUI3Activity::class.java)
            }

            binding.btDemo -> {
                showAd()
                launchActivity(MenuDemoActivity::class.java)
            }

            binding.btRateApp -> this.rateApp(packageName)
            binding.btMoreApp -> this.moreApp()
            binding.btFunction -> {
                showAd()
                launchActivity(MenuFunctionActivity::class.java)
            }

            binding.btGame -> {
                showAd()
                launchActivity(MenuGameActivity::class.java)
            }

            binding.btDatabase -> {
                showAd()
                launchActivity(MenuDatabaseActivity::class.java)
            }

            binding.btPattern -> {
                showAd()
                launchActivity(MenuPatternActivity::class.java)
            }

            binding.btChat -> this.chatMessenger()
            binding.btGithub -> {
                this.openUrlInBrowser(
                    url = "https://github.com/tplloi/base"
                )
            }

            binding.btAdHelper -> {
                showAd()
                launchActivity(cls = AdHelperActivity::class.java, data = {
                    it.putExtra(AD_HELPER_IS_ENGLISH_LANGUAGE, true)
                    it.putExtra(AD_HELPER_COLOR_PRIMARY, Color.RED)
                    it.putExtra(AD_HELPER_COLOR_BACKGROUND, Color.YELLOW)
                    it.putExtra(AD_HELPER_COLOR_STATUS_BAR, Color.GREEN)
                    it.putExtra(AD_HELPER_IS_LIGHT_ICON_STATUS_BAR, true)
                })
            }

            binding.btFbFanpage -> this.likeFacebookFanpage()
            binding.btFrmMore -> {
                showAd()
                launchActivity(MoreActivity::class.java)
            }

            binding.btTutorial -> {
                showAd()
                launchActivity(MenuTutorialActivity::class.java)
            }

            binding.btPicker -> {
                showAd()
                launchActivity(MenuPickerActivity::class.java)
            }

            binding.btNetwork -> {
                showAd()
                launchActivity(MenuNetworkActivity::class.java)
            }

            binding.btSecurity -> {
                showAd()
                launchActivity(MenuSecurityActivity::class.java)
            }

            binding.btService -> {
                showAd()
                launchActivity(MenuServiceActivity::class.java)
            }

            binding.btUtils -> {
                showAd()
                launchActivity(UtilsActivity::class.java)
            }

            binding.btUtilsCore -> {
                showAd()
                launchActivity(UtilsCoreActivity::class.java)
            }

            binding.btFeedback -> {
                this.sendEmail(
                    to = "roy93group@gmail.com",
                    cc = "roy93group@gmail.com",
                    bcc = "roy93group@gmail.com",
                    subject = "Feedback",
                    body = "..."
                )
            }

            binding.btInterviewVNIQActivity -> {
                showAd()
                launchActivity(InterviewVNIQActivity::class.java)
            }

            binding.tvMoreApp -> this.moreApp()
        }
    }

    private fun createAdInter() {
        val enableAdInter = getString(R.string.EnableAdInter) == "true"
        if (enableAdInter) {
            interstitialAd = MaxInterstitialAd(getString(R.string.INTER), this)
            interstitialAd?.let { ad ->
                ad.setListener(object : MaxAdListener {
                    override fun onAdLoaded(p0: MaxAd) {
                        logI("onAdLoaded")
                        retryAttempt = 0
                    }

                    override fun onAdDisplayed(p0: MaxAd) {
                        logI("onAdDisplayed")
                    }

                    override fun onAdHidden(p0: MaxAd) {
                        logI("onAdHidden")
                        // Interstitial Ad is hidden. Pre-load the next ad
                        interstitialAd?.loadAd()
                    }

                    override fun onAdClicked(p0: MaxAd) {
                        logI("onAdClicked")
                    }

                    override fun onAdLoadFailed(p0: String, p1: MaxError) {
                        logI("onAdLoadFailed")
                        retryAttempt++
                        val delayMillis =
                            TimeUnit.SECONDS.toMillis(2.0.pow(min(6, retryAttempt)).toLong())

                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                interstitialAd?.loadAd()
                            }, delayMillis
                        )
                    }

                    override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
                        logI("onAdDisplayFailed")
                        // Interstitial ad failed to display. We recommend loading the next ad.
                        interstitialAd?.loadAd()
                    }

                })
                ad.setRevenueListener {
                    logI("onAdDisplayed")
                }

                // Load the first ad.
                ad.loadAd()
            }
        }
    }

    private fun showAd(runnable: Runnable? = null) {
        val enableAdInter = getString(R.string.EnableAdInter) == "true"
        if (enableAdInter) {
            if (interstitialAd == null) {
                runnable?.run()
            } else {
                interstitialAd?.let { ad ->
                    if (ad.isReady) {
                        showDialogProgress()
                        setDelay(500.getRandomNumber() + 500) {
                            hideDialogProgress()
                            ad.showAd()
                            runnable?.run()
                        }
                    } else {
                        runnable?.run()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Applovin show ad Inter in debug mode", Toast.LENGTH_SHORT).show()
            runnable?.run()
        }
    }
}
