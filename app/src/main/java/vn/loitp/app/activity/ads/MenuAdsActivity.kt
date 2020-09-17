package vn.loitp.app.activity.ads

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.helper.admobrewardedvideo.AdmobRewardedVideoActivity
import com.core.utilities.LActivityUtil
import vn.loitp.app.R
import vn.loitp.app.activity.ads.admobbanner.AdmobBannerActivity
import vn.loitp.app.activity.ads.admobinterstitial.AdmobInterstitialActivity

@LayoutId(R.layout.activity_menu_ads)
class MenuAdsActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false
        findViewById<View>(R.id.bt1).setOnClickListener {
            val intent = Intent(activity, AdmobBannerActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        findViewById<View>(R.id.bt2).setOnClickListener {
            val intent = Intent(activity, AdmobInterstitialActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        findViewById<View>(R.id.bt3).setOnClickListener {
            val intent = Intent(activity, AdmobRewardedVideoActivity::class.java)
            intent.putExtra(AdmobRewardedVideoActivity.APP_ID, getString(R.string.str_app_id))
            intent.putExtra(AdmobRewardedVideoActivity.ID_REWARD, getString(R.string.str_reward))
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

}

