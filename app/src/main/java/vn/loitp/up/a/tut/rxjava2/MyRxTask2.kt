package vn.loitp.up.a.tut.rxjava2

import android.annotation.SuppressLint
import android.os.SystemClock
import android.widget.TextView
import com.loitp.core.ext.d
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MyRxTask2(val tv: TextView?) {
    val logTag: String = javaClass.simpleName

    @SuppressLint("CheckResult", "SetTextI18n")
    fun execute(): Disposable {
        val publishSubject = PublishSubject.create<Int>()
        publishSubject.observeOn(AndroidSchedulers.mainThread()).subscribe {
            tv?.append("\nonProgressUpdate value = $it")
        }
        tv?.text = "\nonPreExecute"
        return Observable.just(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
            .doOnNext { it ->
                it.forEach {
                    d(logTag, "doInBackground $it")
                    publishSubject.onNext(it)
                    SystemClock.sleep(1000)
                }
            }
            .applySchedulers()
            /*.subscribe {
                // onNext
                tv?.append("onPostExecute\n")
            }*/
            .subscribe({
                d(logTag, "onPostExecute onNext")
                tv?.append("\nonPostExecute")
            }, {
                d(logTag, "onPostExecute onError " + it.printStackTrace())
            }, {
                d(logTag, "onPostExecute onComplete")
            })
    }

    private fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }
}
