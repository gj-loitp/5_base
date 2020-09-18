package vn.loitp.app.activity.tutorial.rxjava2

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava2_flowable.*
import vn.loitp.app.R
import java.util.concurrent.TimeUnit

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LayoutId(R.layout.activity_rxjava2_flowable)
@LogTag("IntervalExampleActivity")
class IntervalExampleActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn.setOnClickListener {
            doSomeWork()
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onDestroy() {
        compositeDisposable.clear() // clearing it : do not emit after destroy
        super.onDestroy()
    }

    /*
     * simple example using interval to run task at an interval of 1 sec
     * which start immediately
     */
    private fun doSomeWork() {
        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io()) // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer))
    }

    private val observable: Observable<out Long>
        get() = Observable.interval(0, 1, TimeUnit.SECONDS)

    private val observer: DisposableObserver<Long?>
        get() = object : DisposableObserver<Long?>() {
            override fun onNext(value: Long) {
                textView.append("\nonNext : value : $value")
                logD("\nonNext : value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append("\nonError : ${e.message}")
                logD("\nonError : ${e.message}")
            }

            override fun onComplete() {
                textView.append("\nonComplete")
                logD("\nonComplete")
            }
        }
}
