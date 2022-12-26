package com.loitp.data

import com.loitp.core.base.BaseModel
import org.greenrobot.eventbus.EventBus

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class EventBusData private constructor() {

    companion object {
        val instance = EventBusData()
    }

    class ConnectEvent : BaseModel() {
        var isConnected: Boolean = false
    }

    fun sendConnectChange(isConnected: Boolean) {
        val connectEvent = ConnectEvent()
        connectEvent.isConnected = isConnected
        EventBus.getDefault().post(connectEvent)
    }
}
