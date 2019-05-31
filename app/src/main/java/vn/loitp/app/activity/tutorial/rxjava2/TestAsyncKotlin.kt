package vn.loitp.app.activity.tutorial.rxjava2

import android.os.Looper
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import vn.loitp.core.utilities.LLog

class TestAsyncKotlin(private val count: Int) {
    private val sourceProgression = PublishSubject.create<Int>()
    fun apply(
            onSuccess: ((isResult: Boolean) -> Unit),
            onError: ((throwbale: Throwable) -> Unit),
            onFinished: (() -> Unit)
    ): Disposable {
        //log("prev")
        return Single.create<Boolean> {
            doInBkg(it)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //log("post 1 " + it)
                    onSuccess.invoke(it)
                    onFinished.invoke()
                }, {
                    //log("post 2 " + it.toString())
                    onError.invoke(it)
                    onFinished.invoke()
                })
    }

    fun subscribeProgression(callback: ((int: Int) -> Unit)): Disposable =
            sourceProgression.observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        callback.invoke(it)
                    }

    fun doInBkg(emmiter: SingleEmitter<Boolean>) {
        if (count > 15) {
            emmiter.onError(IllegalArgumentException("Count must <=15"))
            return
        }
        for (i in 0..count) {
            Thread.sleep(1000)
            //log("bkg count: " + i)
            sourceProgression.onNext(i)
        }
        sourceProgression.onComplete()
        emmiter.onSuccess(true)
    }

    private fun log(s: String) {
        LLog.d("loitptest", s + " ->isUI=" + (Looper.myLooper() == Looper.getMainLooper()))
    }
}