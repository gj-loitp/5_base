package vn.loitp.app.activity.security

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_security.*
import vn.loitp.app.R
import vn.loitp.app.activity.security.simple.SimpleEncryptDecryptStringActivity
import vn.loitp.app.activity.security.ssBiometricsAuthentication.SSBiometricsAuthenticationActivity

@LogTag("SecurityMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSecurityActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_security
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
//                    onBackPressed()
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuSecurityActivity::class.java.simpleName
        }
        bt0.setSafeOnClickListener {
            launchActivity(SimpleEncryptDecryptStringActivity::class.java)
        }
        btSSBiometricsAuthenticationActivity.setSafeOnClickListener {
            launchActivity(SSBiometricsAuthenticationActivity::class.java)
        }
    }
}
