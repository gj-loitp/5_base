package vn.loitp.up.a.tut.rxjava2

import android.os.Bundle
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
import vn.loitp.databinding.ARxjava2FlowableBinding
import java.util.concurrent.TimeUnit

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("IntervalExampleActivity")
@IsFullScreen(false)
class IntervalExampleActivity : BaseActivityFont() {

    private lateinit var binding: ARxjava2FlowableBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
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
            this.tvTitle?.text = IntervalExampleActivity::class.java.simpleName
        }
        binding.btn.setSafeOnClickListener {
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
                binding.textView.append("\nonNext : value : $value")
                logD("\nonNext : value : $value")
            }

            override fun onError(e: Throwable) {
                binding.textView.append("\nonError : ${e.message}")
                logD("\nonError : ${e.message}")
            }

            override fun onComplete() {
                binding.textView.append("\nonComplete")
                logD("\nonComplete")
            }
        }
}
