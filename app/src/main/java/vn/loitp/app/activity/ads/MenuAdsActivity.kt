package vn.loitp.app.activity.ads

import android.content.Intent
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.admobrewardedvideo.AdMobRewardedVideoActivity
import com.core.utilities.LActivityUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_menu_ads.*
import vn.loitp.app.R
import vn.loitp.app.activity.ads.admobbanner.AdMobBannerActivity
import vn.loitp.app.activity.ads.admobinterstitial.AdMobInterstitialActivity

@LogTag("MenuAdsActivity")
@IsFullScreen(false)
class MenuAdsActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_ads
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt1.setSafeOnClickListener {
            val intent = Intent(this, AdMobBannerActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        bt2.setSafeOnClickListener {
            val intent = Intent(this, AdMobInterstitialActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        bt3.setSafeOnClickListener {
            val intent = Intent(this, AdMobRewardedVideoActivity::class.java)
            intent.putExtra(AdMobRewardedVideoActivity.APP_ID, getString(R.string.str_app_id))
            intent.putExtra(AdMobRewardedVideoActivity.ID_REWARD, getString(R.string.str_reward))
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }

}

