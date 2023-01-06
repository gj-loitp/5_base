package vn.loitp.a.cv.et.otpView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.et.otp.OTPListener
import kotlinx.android.synthetic.main.a_et_otp.*
import vn.loitp.R

@LogTag("OtpViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class OtpViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_et_otp
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.apply {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = this,
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/aabhasr1/OtpView"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = OtpViewActivityFont::class.java.simpleName
        }

        otpView.requestFocusOTP()
        otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                showShortInformation("The OTP is $otp")
            }
        }

        btError.setSafeOnClickListener {
            otpView.showError()
        }
        btSuccess.setSafeOnClickListener {
            otpView.showSuccess()
        }
        btSetOTP.setSafeOnClickListener {
            otpView.setOTP("654321")
        }
        btGetOTP.setSafeOnClickListener {
            showShortInformation("otp: ${otpView.otp}")
        }
        btResetState.setSafeOnClickListener {
            otpView.resetState()
        }
    }

}
