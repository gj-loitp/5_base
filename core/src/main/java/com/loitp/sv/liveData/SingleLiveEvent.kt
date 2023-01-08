package com.loitp.sv.liveData

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.loitp.core.ext.d
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)
    private val logTag = javaClass::class.java.simpleName

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            d(
                logTag,
                "Multiple observers registered but only one will be notified of changes."
            )
        }
        // Observe the internal MutableLiveData
        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}
