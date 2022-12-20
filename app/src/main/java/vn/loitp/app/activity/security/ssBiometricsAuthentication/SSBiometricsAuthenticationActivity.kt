package vn.loitp.app.activity.security.ssBiometricsAuthentication

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_ss_biometrics_authentication.*
import vn.loitp.app.R
import java.util.concurrent.Executor

@LogTag("SSBiometricsAuthenticationActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SSBiometricsAuthenticationActivity : BaseFontActivity() {

    companion object {
        const val RC_BIOMETRICS_ENROLL = 10
        const val RC_DEVICE_CREDENTIAL_ENROLL = 18
    }

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var executor: Executor
    private lateinit var callBack: BiometricPrompt.AuthenticationCallback
    private var keyguardManager: KeyguardManager? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_ss_biometrics_authentication
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        checkDeviceCanAuthenticateWithBiometrics()
        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/SimformSolutionsPvtLtd/SSBiometricsAuthentication"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = SSBiometricsAuthenticationActivity::class.java.simpleName
        }

        btUnlock.setSafeOnClickListener {
            authenticateWithBiometrics()
        }
    }

    private fun init() {
        executor = ContextCompat.getMainExecutor(this)
        callBack = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                showShortError(getString(R.string.error_unknown))
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                showShortInformation(getString(R.string.message_success))
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                showShortError(getErrorMessage(errorCode))
            }
        }
        biometricPrompt = BiometricPrompt(
            /* activity = */ this,
            /* executor = */ executor,
            /* callback = */ callBack
        )
    }

    private fun checkDeviceCanAuthenticateWithBiometrics() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {

            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                showShortError(getString(R.string.message_no_support_biometrics))
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                showShortError(getString(R.string.message_no_hardware_available))
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                checkAPILevelAndProceed()
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                showShortError(getString(R.string.error_security_update_required))
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                showShortError(getString(R.string.error_unknown))
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                showShortError(getString(R.string.error_unknown))
            }
        }
    }

    private fun biometricsEnrollIntent(): Intent {
        return Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        }
    }

    private fun setUpDeviceLockInAPIBelow23Intent(): Intent {
        return Intent(Settings.ACTION_SECURITY_SETTINGS)
    }

    private fun checkAPILevelAndProceed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            startActivityForResult(setUpDeviceLockInAPIBelow23Intent(), RC_DEVICE_CREDENTIAL_ENROLL)
        } else {
            startActivityForResult(biometricsEnrollIntent(), RC_BIOMETRICS_ENROLL)
        }
    }

    private fun getErrorMessage(errorCode: Int): String {
        return when (errorCode) {
            BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {
                getString(R.string.message_user_app_authentication)
            }
            BiometricPrompt.ERROR_HW_UNAVAILABLE -> {
                getString(R.string.error_hw_unavailable)
            }
            BiometricPrompt.ERROR_UNABLE_TO_PROCESS -> {
                getString(R.string.error_unable_to_process)
            }
            BiometricPrompt.ERROR_TIMEOUT -> {
                getString(R.string.error_time_out)
            }
            BiometricPrompt.ERROR_NO_SPACE -> {
                getString(R.string.error_no_space)
            }
            BiometricPrompt.ERROR_CANCELED -> {
                getString(R.string.error_canceled)
            }
            BiometricPrompt.ERROR_LOCKOUT -> {
                getString(R.string.error_lockout)
            }
            BiometricPrompt.ERROR_VENDOR -> {
                getString(R.string.error_vendor)
            }
            BiometricPrompt.ERROR_LOCKOUT_PERMANENT -> {
                getString(R.string.error_lockout_permanent)
            }
            BiometricPrompt.ERROR_USER_CANCELED -> {
                getString(R.string.error_user_canceled)
            }
            BiometricPrompt.ERROR_NO_BIOMETRICS -> {
                checkAPILevelAndProceed()
                getString(R.string.error_no_biometrics)
            }
            BiometricPrompt.ERROR_HW_NOT_PRESENT -> {
                getString(R.string.error_hw_not_present)
            }
            BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL -> {
                startActivityForResult(biometricsEnrollIntent(), RC_BIOMETRICS_ENROLL)
                getString(R.string.error_no_device_credentials)
            }
            BiometricPrompt.ERROR_SECURITY_UPDATE_REQUIRED -> {
                getString(R.string.error_security_update_required)
            }
            else -> {
                getString(R.string.error_unknown)
            }
        }
    }

    private fun authenticateWithBiometrics() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(getString(R.string.title_biometric_dialog))
            setDescription(getString(R.string.text_description_biometrics_dialog))
            setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
        }.build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager?.let { manager ->
                if (manager.isKeyguardSecure) {
                    biometricPrompt.authenticate(promptInfo)
                } else {
                    startActivityForResult(
                        setUpDeviceLockInAPIBelow23Intent(),
                        RC_DEVICE_CREDENTIAL_ENROLL
                    )
                }
            }
        } else {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}
