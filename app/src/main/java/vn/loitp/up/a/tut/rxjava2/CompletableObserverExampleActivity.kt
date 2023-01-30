package vn.loitp.up.a.tut.rxjava2

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import vn.loitp.R
import vn.loitp.databinding.ARxjava2FlowableBinding
import java.util.concurrent.TimeUnit

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("CompletableObserverExampleActivity")
@IsFullScreen(false)
class CompletableObserverExampleActivity : BaseActivityFont() {

    private lateinit var binding: ARxjava2FlowableBinding

    override fun setLayoutResourceId(): Int {
        return R.layout.a_rxjava2_flowable
    }

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
            this.tvTitle?.text = CompletableObserverExampleActivity::class.java.simpleName
        }
        binding.btn.setSafeOnClickListener {
            doSomeWork()
        }
    }

    /*
     * simple example using CompletableObserver
     */
    private fun doSomeWork() {
        val completable = Completable.timer(2000, TimeUnit.MILLISECONDS)
        completable
            .subscribeOn(Schedulers.io()) // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(completableObserver)
    }

    private val completableObserver: CompletableObserver
        get() = object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {
                logD("onSubscribe : " + d.isDisposed)
            }

            override fun onComplete() {
                binding.textView.append("\nonComplete")
                logD("onComplete")
            }

            override fun onError(e: Throwable) {
                binding.textView.append("\nonError : ${e.message}")
                logD("onError : " + e.message)
            }
        }
}
