package vn.loitp.app.activity.service.demoService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.loitpcore.views.toast.LToast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class DemoService : Service() {
    private val disposables = CompositeDisposable()

    override fun onBind(intent: Intent): IBinder? {
//        Log.d(logTag, "Service onBind")
        return null
    }

    override fun onStartCommand(
        intent: Intent,
        flags: Int,
        startId: Int
    ): Int {
        LToast.show("Service onStartCommand")
        run()
        return START_STICKY
    }

    override fun onDestroy() {
        LToast.show("Service onDestroy")
        disposables.clear()
        run()
        super.onDestroy()
    }

    private fun run() {
        disposables.add(
            getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        )
    }

    private fun getObservable(): Observable<out Long> {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
    }

    private fun getObserver(): DisposableObserver<Long> {
        return object : DisposableObserver<Long>() {

            override fun onNext(value: Long) {
                LToast.show("onNext : value : $value")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {
            }
        }
    }
}
