package vn.loitp.app.activity.demo.firebase.admob

import android.content.Intent
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_firebase_admob.*
import vn.loitp.app.R

@LogTag("FirebaseAdmobActivity")
@IsFullScreen(false)
class FirebaseAdMobActivity : BaseFontActivity() {
    private var mInterstitialAd: InterstitialAd? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_firebase_admob
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd?.adUnitId = getString(R.string.str_f)
        mInterstitialAd?.adListener = object : AdListener() {
            override fun onAdClosed() {
                requestNewInterstitial()
                beginSecondActivity()
            }

            override fun onAdLoaded() {
                btLoadInterstitial?.isEnabled = true
            }

            override fun onAdFailedToLoad(i: Int) {
                logE("onAdFailedToLoad:$i")
            }
        }

        btLoadInterstitial.setOnClickListener {
            if (mInterstitialAd?.isLoaded == true) {
                mInterstitialAd?.show()
            } else {
                beginSecondActivity()
            }
        }

        btLoadInterstitial.isEnabled = mInterstitialAd?.isLoaded ?: false
    }

    private fun requestNewInterstitial() {
        val adRequest = AdRequest.Builder()
                .build()
        mInterstitialAd?.loadAd(adRequest)
    }

    private fun beginSecondActivity() {
        val intent = Intent(this, FirebaseAdmobActivity2::class.java)
        startActivity(intent)
        LActivityUtil.tranIn(this)
    }

    public override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    /**
     * Called when returning to the activity
     */
    public override fun onResume() {
        super.onResume()
        adView?.resume()
        if (mInterstitialAd?.isLoaded == false) {
            requestNewInterstitial()
        }
    }

    public override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }
}
