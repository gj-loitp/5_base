package vn.loitp.app.activity.tutorial.rxjava2

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava2_flowable.*
import vn.loitp.app.R
import java.util.concurrent.TimeUnit

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("IntervalExampleActivity")
@IsFullScreen(false)
class IntervalExampleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_rxjava2_flowable
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
//                    onBackPressed()
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = IntervalExampleActivity::class.java.simpleName
        }
        btn.setSafeOnClickListener {
            doSomeWork()
        }
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
        compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io()) // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
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
