package vn.loitp.app.activity.ads

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
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

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuAdsActivity::class.java.simpleName
        }
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
    }
}
