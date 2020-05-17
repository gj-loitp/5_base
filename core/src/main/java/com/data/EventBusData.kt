package com.data

import org.greenrobot.eventbus.EventBus

/**
 * Created by www.muathu@gmail.com on 10/21/2017.
 */

class EventBusData private constructor() {

    class ConnectEvent {
        var isConnected: Boolean = false
    }

    fun sendConnectChange(isConnected: Boolean) {
        val connectEvent = ConnectEvent()
        connectEvent.isConnected = isConnected
        EventBus.getDefault().post(connectEvent)
    }

    companion object {
        val instance = EventBusData()
    }
}
