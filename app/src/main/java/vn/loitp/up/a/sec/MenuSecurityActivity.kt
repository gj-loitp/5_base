package vn.loitp.up.a.sec

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ASecMenuBinding
import vn.loitp.up.a.sec.biometric.BiometricActivity
import vn.loitp.up.a.sec.biometricLopez.BiometricLopezActivity
import vn.loitp.up.a.sec.rxbiometric.RxBiometricActivity
import vn.loitp.up.a.sec.simple.SimpleEncryptDecryptStringActivity
import vn.loitp.up.a.sec.ssBiometricsAuthentication.SSBiometricsAuthenticationActivity

@LogTag("SecurityMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSecurityActivity : BaseActivityFont() {

    private lateinit var binding: ASecMenuBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASecMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuSecurityActivity::class.java.simpleName
        }

        binding.btBiometric.setSafeOnClickListener {
            launchActivity(BiometricActivity::class.java)
        }
        binding.btBiometricLopez.setSafeOnClickListener {
            launchActivity(BiometricLopezActivity::class.java)
        }
        binding.btRxBiometric.setSafeOnClickListener {
            launchActivity(RxBiometricActivity::class.java)
        }
        binding.btSimple.setSafeOnClickListener {
            launchActivity(SimpleEncryptDecryptStringActivity::class.java)
        }
        binding.btSSBiometricsAuthenticationActivity.setSafeOnClickListener {
            launchActivity(SSBiometricsAuthenticationActivity::class.java)
        }
    }
}
