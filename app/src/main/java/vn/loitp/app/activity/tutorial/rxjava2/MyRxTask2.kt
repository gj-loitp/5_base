package vn.loitp.app.activity.tutorial.rxjava2

import android.os.SystemClock
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import vn.loitp.core.utilities.LLog

class MyRxTask2(val tv: TextView?) {
    val TAG = javaClass.simpleName

    fun execute() {
        val publishSubject = PublishSubject.create<Int>()
        publishSubject.observeOn(AndroidSchedulers.mainThread()).subscribe {
            LLog.d(TAG, "onProgressUpdate value = $it")
            tv?.append("onProgressUpdate value = $it\n")
        }
        LLog.d(TAG, "onPreExecute")
        tv?.setText("onPreExecute\n")
        Observable.just(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
                .doOnNext {
                    LLog.d(TAG, "doInBackground")
                    it.forEach {
                        publishSubject.onNext(it)
                        SystemClock.sleep(1000)
                    }
                }
                .applySchedulers()
                .subscribe {
                    // onNext
                    LLog.d(TAG, "onPostExecute")
                    tv?.append("onPostExecute\n")
                }
    }

    private fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }
}