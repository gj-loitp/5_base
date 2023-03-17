package vn.loitp.up.a.cv.et.otpView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.et.otp.OTPListener
import vn.loitp.R
import vn.loitp.databinding.AEtOtpBinding

@LogTag("OtpViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class OtpViewActivity : BaseActivityFont() {
    private lateinit var binding: AEtOtpBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AEtOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/aabhasr1/OtpView"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = OtpViewActivity::class.java.simpleName
        }

        binding.otpView.requestFocusOTP()
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                showShortInformation("The OTP is $otp")
            }
        }

        binding.btError.setSafeOnClickListener {
            binding.otpView.showError()
        }
        binding.btSuccess.setSafeOnClickListener {
            binding.otpView.showSuccess()
        }
        binding.btSetOTP.setSafeOnClickListener {
            binding.otpView.setOTP("654321")
        }
        binding.btGetOTP.setSafeOnClickListener {
            showShortInformation("otp: ${binding.otpView.otp}")
        }
        binding.btResetState.setSafeOnClickListener {
            binding.otpView.resetState()
        }
    }

}
