package vn.loitp.a.tut.rxjava2

import android.os.Looper
import com.loitp.core.utilities.LLog
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class TestAsyncKotlin(private val count: Int) {
    private val sourceProgression = PublishSubject.create<Int>()
    fun apply(
        onSuccess: ((isResult: Boolean) -> Unit),
        onError: ((throwable: Throwable) -> Unit),
        onDispose: (() -> Unit),
        onFinished: (() -> Unit)
    ): Disposable {
        log("prev")
        return Single.create {
            doInBkg(it)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnDispose {
                onDispose.invoke()
            }
            .subscribe({
                log("post 1 $it")
                onSuccess.invoke(it)
                onFinished.invoke()
            }, {
                log("post 2 $it")
                onError.invoke(it)
                onFinished.invoke()
            })
    }

    fun subscribeProgression(callback: ((int: Int) -> Unit)): Disposable =
        sourceProgression.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                callback.invoke(it)
            }

    private fun doInBkg(emmiter: SingleEmitter<Boolean>) {
        if (count > 15) {
            emmiter.onError(IllegalArgumentException("Count must <=15"))
            return
        }
        for (i in 0..count) {
            try {
                Thread.sleep(1000)
            } catch (e: Exception) {
                log("Exception $e")
                sourceProgression.onComplete()
                emmiter.tryOnError(IllegalArgumentException("ahihi"))
                break
            }
            log("bkg count: $i")
            sourceProgression.onNext(i)
        }
        sourceProgression.onComplete()
        emmiter.onSuccess(true)
    }

    private fun log(s: String) {
        LLog.d("TestAsyncKotlin", s + " ->isUI=" + (Looper.myLooper() == Looper.getMainLooper()))
    }
}
