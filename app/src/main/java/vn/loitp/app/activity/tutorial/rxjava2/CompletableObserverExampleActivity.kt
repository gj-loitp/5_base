package vn.loitp.app.activity.tutorial.rxjava2

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava2_flowable.*
import vn.loitp.app.R
import java.util.concurrent.TimeUnit

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("CompletableObserverExampleActivity")
@IsFullScreen(false)
class CompletableObserverExampleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_rxjava2_flowable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn.setOnClickListener {
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
                textView.append("\nonComplete")
                logD("onComplete")
            }

            override fun onError(e: Throwable) {
                textView.append("\nonError : ${e.message}")
                logD("onError : " + e.message)
            }
        }
}
