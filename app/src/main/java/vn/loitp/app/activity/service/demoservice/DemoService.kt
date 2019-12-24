package vn.loitp.app.activity.service.demoservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.core.utilities.LLog

import com.views.LToast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class DemoService : Service() {
    private val TAG = "TAG" + javaClass.simpleName
    private val disposables = CompositeDisposable()
    override fun onBind(intent: Intent): IBinder? {
        LLog.d(TAG, "Service onBind")
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        LToast.show(this, "Service onStartCommand")
        LLog.d(TAG, "Service onStartCommand")
        run()
        return START_STICKY
    }

    override fun onDestroy() {
        LLog.d(TAG, "Service onDestroy")
        LToast.show(this, "Service onDestroy")
        disposables.clear()
        run()
        super.onDestroy()
    }

    private fun run() {
        disposables.add(getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()))
    }

    private fun getObservable(): Observable<out Long> {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
    }

    private fun getObserver(): DisposableObserver<Long> {
        return object : DisposableObserver<Long>() {

            override fun onNext(value: Long) {
                LLog.d(TAG, "onNext : value : $value")
                LToast.show(applicationContext, "onNext : value : $value")
            }

            override fun onError(e: Throwable) {
                LLog.d(TAG, "onError : " + e.message)
            }

            override fun onComplete() {
                LLog.d(TAG, "onComplete")
            }
        }
    }
}