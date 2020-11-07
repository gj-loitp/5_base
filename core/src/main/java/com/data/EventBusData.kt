package com.data

import com.core.base.BaseModel
import org.greenrobot.eventbus.EventBus

/**
 * Created by www.muathu@gmail.com on 10/21/2017.
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
