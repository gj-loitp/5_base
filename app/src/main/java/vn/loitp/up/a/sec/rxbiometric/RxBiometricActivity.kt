package vn.loitp.up.a.sec.rxbiometric

import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.github.pwittchen.rxbiometric.library.RxBiometric
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationError
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationFail
import com.github.pwittchen.rxbiometric.library.throwable.BiometricNotSupported
import com.github.pwittchen.rxbiometric.library.validation.RxPreconditions
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import vn.loitp.R
import vn.loitp.databinding.ASecRxBiometricBinding

@LogTag("RxBiometricActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class RxBiometricActivity : BaseActivityFont() {

    private lateinit var binding: ASecRxBiometricBinding
    private var disposable: Disposable? = null

//    override fun setLayoutResourceId(): Int {
//        return R.layout.a_sec_rx_biometric
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASecRxBiometricBinding.inflate(layoutInflater)
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
                        url = "https://github.com/pwittchen/RxBiometric"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = RxBiometricActivity::class.java.simpleName
        }

        binding.btAuthenticate.setSafeOnClickListener {
            authenticate()
        }
    }

    private fun authenticate() {
        disposable = RxPreconditions.hasBiometricSupport(this).flatMapCompletable {
            if (!it) Completable.error(BiometricNotSupported())
            else RxBiometric.title("title").description("description")
                .negativeButtonText("cancel").negativeButtonListener { _, _ ->
                    showShortError("cancel")
                }.executor(ActivityCompat.getMainExecutor(this)).build().authenticate(this)
        }.observeOn(AndroidSchedulers.mainThread()).subscribeBy(onComplete = {
            showShortInformation("authenticated")
        }, onError = {
            when (it) {
                is AuthenticationError -> showShortError("error: ${it.errorCode} ${it.errorMessage}")
                is AuthenticationFail -> showShortError("fail")
                else -> {
                    showShortInformation("other error")
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        disposable?.apply {
            if (!this.isDisposed) {
                this.dispose()
            }
        }
    }
}
