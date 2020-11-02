package vn.loitp.app.activity.tutorial.rxjava2

import android.os.Bundle
import android.os.SystemClock
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_java2_disposable.*
import vn.loitp.app.R

//https://www.vogella.com/tutorials/RxJava/article.html

@LogTag("DisposableExampleActivity")
@IsFullScreen(false)
class DisposableExampleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_rx_java2_disposable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn.setOnClickListener {
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
        textView.append("\nLoading...")
        compositeDisposable.add(sampleObservable()
                .subscribeOn(Schedulers.io()) // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String?>() {
                    override fun onComplete() {
                        textView.append("\nonComplete")
                        logD("onComplete")
                    }

                    override fun onError(e: Throwable) {
                        textView.append("\nonError : ${e.message}")
                        logE("onError : " + e.message)
                    }

                    override fun onNext(value: String) {
                        textView.append("\nonNext : value : $value")
                        logD("onNext value : $value")
                    }
                }))
    }

    private fun sampleObservable(): Observable<String> {
        return Observable.defer {
            // Do some long running operation
            SystemClock.sleep(2000)
            Observable.just("one", "two", "three", "four", "five")
        }
    }
}
