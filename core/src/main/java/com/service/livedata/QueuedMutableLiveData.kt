package com.service.livedata

import androidx.lifecycle.MutableLiveData
import java.util.* // ktlint-disable no-wildcard-imports

class QueuedMutableLiveData<T> : MutableLiveData<T>() {
    private val queue = LinkedList<T?>()

    override fun setValue(value: T) {
        super.setValue(value)
        synchronized(queue) {
            queue.pollFirst()
            queue.peekFirst()?.run {
                super.postValue(this)
            }
        }
    }

    override fun postValue(value: T?) {
        synchronized(queue) {
            queue.add(value)
            if (queue.size == 1) {
                super.postValue(value)
            }
        }
    }
}
