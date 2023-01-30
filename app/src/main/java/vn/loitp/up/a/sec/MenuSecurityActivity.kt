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
import vn.loitp.a.sec.rxbiometric.RxBiometricActivityFont
import vn.loitp.a.sec.simple.SimpleEncryptDecryptStringActivityFont
import vn.loitp.databinding.ASecMenuBinding
import vn.loitp.up.a.sec.ssBiometricsAuthentication.SSBiometricsAuthenticationActivity

@LogTag("SecurityMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSecurityActivity : BaseActivityFont() {

    private lateinit var binding: ASecMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

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
        binding.btRxBiometric.setSafeOnClickListener {
            launchActivity(RxBiometricActivityFont::class.java)
        }
        binding.btSimple.setSafeOnClickListener {
            launchActivity(SimpleEncryptDecryptStringActivityFont::class.java)
        }
        binding.btSSBiometricsAuthenticationActivity.setSafeOnClickListener {
            launchActivity(SSBiometricsAuthenticationActivity::class.java)
        }
    }
}
