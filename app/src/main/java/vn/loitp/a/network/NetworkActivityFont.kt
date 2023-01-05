package vn.loitp.a.network

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LConnectivityUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.data.EventBusData
import com.loitp.func.network.TrafficUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.a_network.*
import vn.loitp.R
import java.util.concurrent.TimeUnit

@LogTag("NetworkActivity")
@IsFullScreen(false)
class NetworkActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_network
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
            this.tvTitle?.text = NetworkActivityFont::class.java.simpleName
        }
        showStatus(LConnectivityUtil.isConnected())
        btn.setSafeOnClickListener {
            doSomeWork()
        }
    }

    override fun onNetworkChange(event: EventBusData.ConnectEvent) {
        super.onNetworkChange(event)
        logD("onNetworkChange: " + event.isConnected)
        showStatus(event.isConnected)
    }

    @SuppressLint("SetTextI18n")
    private fun showStatus(isConnected: Boolean) {
        textView.text = "isConnected: $isConnected"
    }

    override fun onDestroy() {
        compositeDisposable.clear() // clearing it : do not emit after destroy
        super.onDestroy()
    }

    private fun doSomeWork() {
        compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io()) // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

    private val observable: Observable<out Long>
        get() = Observable.interval(0, 2, TimeUnit.SECONDS)

    private val observer: DisposableObserver<Long?>
        get() = object : DisposableObserver<Long?>() {
            @SuppressLint("SetTextI18n")
            override fun onNext(value: Long) {
                logD("=============================onNext : $value")
                logD("isWifiConnected ${TrafficUtils.isWifiConnected(this@NetworkActivityFont)}")
                logD("getNetworkSpeed ${TrafficUtils.getNetworkSpeed()}")

                tv.post {
                    tv.text =
                        "isWifiConnected ${TrafficUtils.isWifiConnected(this@NetworkActivityFont)}\ngetNetworkSpeed ${TrafficUtils.getNetworkSpeed()}"
                }
            }

            override fun onError(e: Throwable) {
                logD("onError : ${e.message}")
            }

            override fun onComplete() {
                logD("onComplete")
            }
        }
}
