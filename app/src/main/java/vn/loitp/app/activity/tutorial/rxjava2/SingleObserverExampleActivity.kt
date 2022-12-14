package vn.loitp.app.activity.tutorial.rxjava2

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rxjava2_flowable.*
import vn.loitp.app.R

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("SingleObserverExampleActivity")
@IsFullScreen(false)
class SingleObserverExampleActivity : BaseFontActivity() {

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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = SingleObserverExampleActivity::class.java.simpleName
        }
        btn.setSafeOnClickListener {
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
}
