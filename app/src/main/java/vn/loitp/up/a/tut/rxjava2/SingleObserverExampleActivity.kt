package vn.loitp.up.a.tut.rxjava2

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import vn.loitp.R
import vn.loitp.databinding.ARxjava2FlowableBinding

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("SingleObserverExampleActivity")
@IsFullScreen(false)
class SingleObserverExampleActivity : BaseActivityFont() {

    private lateinit var binding: ARxjava2FlowableBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARxjava2FlowableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = SingleObserverExampleActivity::class.java.simpleName
        }
        binding.btn.setSafeOnClickListener {
            doSomeWork()
        }
    }

    /*
     * simple example using SingleObserver
     */
    private fun doSomeWork() {
        Single.just("Amit").subscribe(singleObserver)
    }

    private val singleObserver: SingleObserver<String>
        get() = object : SingleObserver<String> {
            override fun onSubscribe(d: Disposable) {
                logD("onSubscribe : " + d.isDisposed)
            }

            override fun onSuccess(value: String) {
                binding.textView.append("\nonSuccess : value : $value")
                logD("onSuccess value : $value")
            }

            override fun onError(e: Throwable) {
                binding.textView.append("\nonError : ${e.message}")
                logD("onError : " + e.message)
            }
        }
}
