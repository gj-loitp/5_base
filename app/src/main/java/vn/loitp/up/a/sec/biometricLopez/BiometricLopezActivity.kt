package vn.loitp.up.a.sec.biometricLopez

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.addFragment
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ASecBiometricLopezBinding

@LogTag("BiometricLopezActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class BiometricLopezActivity : BaseActivityFont() {

    private lateinit var binding: ASecBiometricLopezBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASecBiometricLopezBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/lopspower/Biometric"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = BiometricLopezActivity::class.java.simpleName
        }

        this.addFragment(
            containerFrameLayoutIdRes = R.id.flContainer,
            fragment = FrmTestBiometric(),
            isAddToBackStack = false
        )
    }

}
