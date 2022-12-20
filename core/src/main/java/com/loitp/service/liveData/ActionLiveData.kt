package com.loitp.service.liveData

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
open class ActionLiveData<T> : MutableLiveData<T>() {

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        // Being strict about the observer numbers is up to you
        // I thought it made sense to only allow one to handle the event
//        if (hasObservers()) {
//            throw Throwable("Only one observer at a time may subscribe to a ActionLiveData")
//        }

        super.observe(
            owner,
            Observer { data ->
                // We ignore any null values and early return
                if (data == null) return@Observer
                observer.onChanged(data)
                // We set the status to null straight after emitting the change to the observer
                value = null
                // This means that the state of the data will always be null / non existent
                // It will only be available to the observer in its callback and since we do not emit null values
                // the observer never receives a null status and any observers resuming do not receive the last event.
                // Therefore it only emits to the observer the single searchCustomerAction so you are free to show messages over and over again
                // Or launch an activity/dialog or anything that should only happen once per searchCustomerAction / click :).
            }
        )
    }

    // Just a nicely named method that wraps setting the status
    @MainThread
    @Suppress("unused")
    fun sendAction(data: T) {
        value = data
    }

    fun postAction(data: T) {
        postValue(data)
    }
}
