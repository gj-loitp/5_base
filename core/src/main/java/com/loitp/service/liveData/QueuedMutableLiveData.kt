package com.loitp.service.liveData

import androidx.lifecycle.MutableLiveData
import java.util.* // ktlint-disable no-wildcard-imports

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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
