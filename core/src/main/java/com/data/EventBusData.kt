package com.data

import org.greenrobot.eventbus.EventBus

/**
 * Created by www.muathu@gmail.com on 10/21/2017.
 */

class EventBusData private constructor() {

    class ConnectEvent {
        var isConnected: Boolean = false
        var isConnectedFast: Boolean = false
        var isConnectedWifi: Boolean = false
        var isConnectedMobile: Boolean = false
    }

    fun sendConnectChange(isConnected: Boolean, isConnectedFast: Boolean, isConnectedWifi: Boolean, isConnectedMobile: Boolean) {
        val connectEvent = ConnectEvent()
        connectEvent.isConnected = isConnected
        connectEvent.isConnectedFast = isConnectedFast
        connectEvent.isConnectedWifi = isConnectedWifi
        connectEvent.isConnectedMobile = isConnectedMobile
        EventBus.getDefault().post(connectEvent)
    }

    companion object {
        val instance = EventBusData()
    }
}
