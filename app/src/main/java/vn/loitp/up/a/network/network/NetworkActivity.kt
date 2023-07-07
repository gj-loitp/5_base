package vn.loitp.up.a.network.network

import android.annotation.SuppressLint
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.isConnected
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.data.EventBusData
import com.loitp.func.network.TrafficUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import vn.loitp.R
import vn.loitp.databinding.ANetworkBinding
import java.util.concurrent.TimeUnit

@LogTag("NetworkActivity")
@IsFullScreen(false)
class NetworkActivity : BaseActivityFont() {

    private lateinit var binding: ANetworkBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ANetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = NetworkActivity::class.java.simpleName
        }
        showStatus(this.isConnected())
        binding.btn.setSafeOnClickListener {
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
        binding.textView.text = "isConnected: $isConnected"
    }

    override fun onDestroy() {
        compositeDisposable.clear() // clearing it : do not emit after destroy
        super.onDestroy()
    }

    private fun doSomeWork() {
        compositeDisposable.add(
            observable.subscribeOn(Schedulers.io()) // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer)
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

                binding.tv.post {
                    binding.tv.text =
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
