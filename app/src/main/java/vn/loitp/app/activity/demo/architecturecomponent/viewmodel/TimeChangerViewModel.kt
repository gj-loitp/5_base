package vn.loitp.app.activity.demo.architecturecomponent.viewmodel

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

    private fun startTimer() {
        val disposable = Observable.interval(1, 1, TimeUnit.SECONDS)
                .subscribe({
                    timerValue.postValue(System.currentTimeMillis())
                }, Throwable::printStackTrace)
    }
}