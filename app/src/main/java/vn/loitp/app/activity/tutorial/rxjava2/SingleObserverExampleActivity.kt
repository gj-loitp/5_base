package vn.loitp.app.activity.tutorial.rxjava2

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rxjava2_flowable.*
import vn.loitp.app.R

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LayoutId(R.layout.activity_rxjava2_flowable)
class SingleObserverExampleActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn.setOnClickListener {
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
                textView.append("\nonSuccess : value : $value")
                logD("onSuccess value : $value")
            }

            override fun onError(e: Throwable) {
                textView.append("\nonError : ${e.message}")
                logD("onError : " + e.message)
            }
        }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
