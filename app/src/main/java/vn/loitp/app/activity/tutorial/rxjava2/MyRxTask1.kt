package vn.loitp.app.activity.tutorial.rxjava2

import android.annotation.SuppressLint
import android.os.SystemClock
import android.widget.TextView
import com.core.utilities.LLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyRxTask1(val tv: TextView?) {
    val logTag = javaClass.simpleName

    @SuppressLint("SetTextI18n")
    fun execute(): Disposable {
        LLog.d(logTag, "onPreExecute")
        tv?.text = "onPreExecute"
        return Observable.just(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
                .flatMap {
                    LLog.d(logTag, "doInBackground")
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
                    LLog.d(logTag, "onProgressUpdate value = $it")
                    tv?.append("\nonProgressUpdate value = $it")
                }, {
                    // onError
                    LLog.d(logTag, "onError")
                    tv?.append("\nonError $it")
                }, {
                    // onComplete
                    LLog.d(logTag, "onPostExecute")
                    tv?.append("\nonPostExecute")
                })
    }

    private fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }
}
