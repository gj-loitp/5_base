package vn.loitp.app.activity.network

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LConnectivityUtil
import com.data.EventBusData
import com.function.network.TrafficUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_network.*
import vn.loitp.app.R
import java.util.concurrent.TimeUnit

@LogTag("NetworkActivity")
@IsFullScreen(false)
class NetworkActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_network
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showStatus(LConnectivityUtil.isConnected())
        btn.setOnClickListener {
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
                logD("isWifiConnected ${TrafficUtils.isWifiConnected(this@NetworkActivity)}")
                logD("getNetworkSpeed ${TrafficUtils.getNetworkSpeed()}")

                tv.post {
                    tv.text =
                        "isWifiConnected ${TrafficUtils.isWifiConnected(this@NetworkActivity)}\ngetNetworkSpeed ${TrafficUtils.getNetworkSpeed()}"
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
