package vn.loitp.a.tut.rxjava2

import android.annotation.SuppressLint
import android.os.SystemClock
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyRxTask1(val tv: TextView?) {

    @SuppressLint("SetTextI18n")
    fun execute(): Disposable {
        tv?.text = "onPreExecute"
        return Observable.just(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
            .flatMap {
                Observable.create<Int> { emitter ->
                    it.forEach {
                        emitter.onNext(it)
                        SystemClock.sleep(1000)
                    }
                    emitter.onComplete()
                }
            }
            .applySchedulers()
            .subscribe({
                // onNext
                tv?.append("\nonProgressUpdate value = $it")
            }, {
                // onError
                tv?.append("\nonError $it")
            }, {
                // onComplete
                tv?.append("\nonPostExecute")
            })
    }

    private fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }
}
