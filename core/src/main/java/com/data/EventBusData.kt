package com.data

import org.greenrobot.eventbus.EventBus

/**
 * Created by www.muathu@gmail.com on 10/21/2017.
 */

class EventBusData private constructor() {

    companion object {
        val instance = EventBusData()
    }

    class ConnectEvent {
        var isConnected: Boolean = false
    }

    fun sendConnectChange(isConnected: Boolean) {
        val connectEvent = ConnectEvent()
        connectEvent.isConnected = isConnected
        EventBus.getDefault().post(connectEvent)
    }

    class ThemeEvent {
        var isDarkTheme: Boolean = false
    }

    fun sendThemeChange(isDarkTheme: Boolean) {
        val themeEvent = ThemeEvent()
        themeEvent.isDarkTheme = isDarkTheme
        EventBus.getDefault().post(themeEvent)
    }
}
