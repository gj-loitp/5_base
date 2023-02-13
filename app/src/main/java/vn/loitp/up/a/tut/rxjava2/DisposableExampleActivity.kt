package vn.loitp.up.a.tut.rxjava2

import android.os.Bundle
import android.os.SystemClock
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import vn.loitp.R
import vn.loitp.databinding.ARxJava2DisposableBinding

// https://www.vogella.com/tutorials/RxJava/article.html

@LogTag("DisposableExampleActivity")
@IsFullScreen(false)
class DisposableExampleActivity : BaseActivityFont() {

    private lateinit var binding: ARxJava2DisposableBinding


    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARxJava2DisposableBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = DisposableExampleActivity::class.java.simpleName
        }
        binding.btn.setSafeOnClickListener {
            doSomeWork()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear() // do not send event after activity has been destroyed
    }

    /*
     * Example to understand how to use disposables.
     * disposables is cleared in onDestroy of this activity.
     */
    private fun doSomeWork() {
        binding.textView.append("\nLoading...")
        compositeDisposable.add(
            sampleObservable()
                .subscribeOn(Schedulers.io()) // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String?>() {
                    override fun onComplete() {
                        binding.textView.append("\nonComplete")
                        logD("onComplete")
                    }

                    override fun onError(e: Throwable) {
                        binding.textView.append("\nonError : ${e.message}")
                        logE("onError : " + e.message)
                    }

                    override fun onNext(value: String) {
                        binding.textView.append("\nonNext : value : $value")
                        logD("onNext value : $value")
                    }
                })
        )
    }

    private fun sampleObservable(): Observable<String> {
        return Observable.defer {
            // Do some long running operation
            SystemClock.sleep(2000)
            Observable.just("one", "two", "three", "four", "five")
        }
    }
}
