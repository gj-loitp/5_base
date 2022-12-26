package vn.loitp.a.demo.architectureComponent.vm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class TimeChangerViewModel : ViewModel() {
    val timerValue = MutableLiveData<Long>()

    init {
        timerValue.value = System.currentTimeMillis()
        startTimer()
    }

    @SuppressLint("CheckResult")
    private fun startTimer() {
        Observable.interval(
            /* initialDelay = */ 1,
            /* period = */ 1,
            /* unit = */ TimeUnit.SECONDS
        )
            .subscribe({
                timerValue.postValue(System.currentTimeMillis())
            }, Throwable::printStackTrace)
    }
}
