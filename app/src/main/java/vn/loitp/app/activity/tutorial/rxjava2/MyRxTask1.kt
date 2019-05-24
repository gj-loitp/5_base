package vn.loitp.app.activity.tutorial.rxjava2

import android.os.SystemClock
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import vn.loitp.core.utilities.LLog

class MyRxTask1 {
    val TAG = javaClass.simpleName

    fun execute(): Disposable {
        LLog.d(TAG, "onPreExecute")
        return Observable.just(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
                .flatMap {
                    LLog.d(TAG, "doInBackground")
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
                    LLog.d(TAG, "onProgressUpdate value = $it")
                }, {
                    // onError
                }, {
                    // onComplete
                    LLog.d(TAG, "onPostExecute")
                })
    }

    private fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }
}