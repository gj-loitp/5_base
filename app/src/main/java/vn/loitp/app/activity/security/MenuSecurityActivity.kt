package vn.loitp.app.activity.security

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
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
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
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
