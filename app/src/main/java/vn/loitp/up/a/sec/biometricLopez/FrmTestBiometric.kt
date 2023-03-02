package vn.loitp.up.a.sec.biometricLopez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.loitp.annotation.LogTag
import com.loitp.core.ext.setSafeOnClickListener
import com.mikhaellopez.biometric.BiometricHelper
import com.mikhaellopez.biometric.BiometricPromptInfo
import vn.loitp.databinding.FTestBiometricLopezBinding

/**
 * Created by Loitp on 02,March,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("FrmTest")
class FrmTestBiometric : Fragment() {
    private lateinit var binding: FTestBiometricLopezBinding
//    private val biometricHelper = BiometricHelper(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FTestBiometricLopezBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    fun setupViews() {
        val biometricHelper = BiometricHelper(this)
        binding.btBiometricLopez.isVisible = biometricHelper.biometricEnable()

        // BiometricType = FACE, FINGERPRINT, IRIS, MULTIPLE or NONE
        val biometricType = biometricHelper.getBiometricType()
        toast("biometricType $biometricType")

        binding.btBiometricLopez.setSafeOnClickListener {

            biometricHelper.showBiometricPrompt(BiometricPromptInfo(
                title = "Title", // Mandatory
                negativeButtonText = "Cancel", // Mandatory
                subtitle = "Subtitle",
                description = "Description",
                confirmationRequired = true
            ), onError = { errorCode: Int, errString: CharSequence ->
                toast("onError errorCode $errorCode, errString $errString")
            }, onFailed = {
                toast("onFailed")
            }, onSuccess = { result: BiometricPrompt.AuthenticationResult ->
                toast("onSuccess $result")
            })
        }
    }

    private fun toast(msg: String) {
        (activity as? BiometricLopezActivity)?.showSnackBarInfor(msg)
    }
}