package vn.loitp.app.activity.tutorial.rxjava2

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import io.reactivex.Flowable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rxjava2_flowable.*
import vn.loitp.app.R

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("FlowableExampleActivity")
@IsFullScreen(false)
class FlowAbleExampleActivity : BaseFontActivity() {

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
     * simple example using Flowable
     */
    private fun doSomeWork() {
        val observable = Flowable.just(1, 2, 3, 4, 1)
        observable.reduce(50, { t1: Int, t2: Int -> t1 + t2 }).subscribe(observer)
    }

    private val observer: SingleObserver<Int>
        get() = object : SingleObserver<Int> {
            override fun onSubscribe(d: Disposable) {
                logD("onSubscribe : " + d.isDisposed)
            }

            override fun onSuccess(value: Int) {
                textView.append("\nonSuccess : value : $value")
                logD("onSuccess : value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append("\nonError : ${e.message}")
                logD("onError : " + e.message)
            }
        }
}
