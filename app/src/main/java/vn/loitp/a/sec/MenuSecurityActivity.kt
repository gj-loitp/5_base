package vn.loitp.a.sec

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_sec_menu.*
import vn.loitp.R
import vn.loitp.a.sec.rxbiometric.RxBiometricActivityFont
import vn.loitp.a.sec.simple.SimpleEncryptDecryptStringActivityFont

import vn.loitp.a.sec.ssBiometricsAuthentication.SSBiometricsAuthenticationActivityFont

@LogTag("SecurityMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSecurityActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sec_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuSecurityActivity::class.java.simpleName
        }
        btRxBiometric.setSafeOnClickListener {
            launchActivity(RxBiometricActivityFont::class.java)
        }
        btSimple.setSafeOnClickListener {
            launchActivity(SimpleEncryptDecryptStringActivityFont::class.java)
        }
        btSSBiometricsAuthenticationActivity.setSafeOnClickListener {
            launchActivity(SSBiometricsAuthenticationActivityFont::class.java)
        }
    }
}
