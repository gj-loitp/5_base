package vn.loitp.app.activity.tutorial.rxjava2

import android.annotation.SuppressLint
import android.os.SystemClock
import android.widget.TextView
import com.core.utilities.LLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MyRxTask2(val tv: TextView?) {
    val logTag = javaClass.simpleName

    @SuppressLint("CheckResult", "SetTextI18n")
    fun execute(): Disposable {
        val publishSubject = PublishSubject.create<Int>()
        publishSubject.observeOn(AndroidSchedulers.mainThread()).subscribe {
            LLog.d(logTag, "onProgressUpdate value = $it")
            tv?.append("\nonProgressUpdate value = $it")
        }
        LLog.d(logTag, "onPreExecute")
        tv?.text = "\nonPreExecute"
        return Observable.just(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
                .doOnNext {
                    it.forEach {
                        LLog.d(logTag, "doInBackground " + it)
                        publishSubject.onNext(it)
                        SystemClock.sleep(1000)
                    }
                }
                .applySchedulers()
                /*.subscribe {
                    // onNext
                    LLog.d(TAG, "onPostExecute")
                    tv?.append("onPostExecute\n")
                }*/
                .subscribe({
                    LLog.d(logTag, "onPostExecute onNext")
                    tv?.append("\nonPostExecute")
                }, {
                    LLog.d(logTag, "onPostExecute onError")
                }, {
                    LLog.d(logTag, "onPostExecute onComplete")
                })
    }

    private fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }
}
