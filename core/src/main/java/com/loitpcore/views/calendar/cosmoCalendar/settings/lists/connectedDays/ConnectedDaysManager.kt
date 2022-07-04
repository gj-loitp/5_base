package com.loitpcore.views.calendar.cosmoCalendar.settings.lists.connectedDays

import com.loitpcore.views.calendar.cosmoCalendar.model.Day
import com.loitpcore.views.calendar.cosmoCalendar.utils.DateUtils.getCalendar
import java.util.*

class ConnectedDaysManager private constructor() {

    companion object {
        private var mInstance: ConnectedDaysManager? = null

        @kotlin.jvm.JvmStatic
        val instance: ConnectedDaysManager?
            get() {
                if (mInstance == null) {
                    mInstance = ConnectedDaysManager()
                }
                return mInstance
            }
    }

    private var connectedDaysList: MutableList<ConnectedDays>? = null

    fun getConnectedDaysList(): List<ConnectedDays>? {
        return connectedDaysList
    }

    fun setConnectedDaysList(connectedDaysList: MutableList<ConnectedDays>?) {
        this.connectedDaysList = connectedDaysList
    }

    fun addConnectedDays(connectedDays: ConnectedDays) {
        if (connectedDaysList == null) {
            connectedDaysList = ArrayList()
        }
        connectedDaysList?.add(connectedDays)
    }

    val isAnyConnectedDays: Boolean
        get() = !(connectedDaysList.isNullOrEmpty())

    fun applySettingsToDay(day: Day) {
        connectedDaysList?.forEach { connectedDays ->
            for (dayLong in connectedDays.days) {
                val disabledDayCalendar = getCalendar(dayLong)
                if (day.calendar[Calendar.YEAR] == disabledDayCalendar[Calendar.YEAR]
                        && day.calendar[Calendar.DAY_OF_YEAR] == disabledDayCalendar[Calendar.DAY_OF_YEAR]) {
                    day.isFromConnectedCalendar = true
                    day.connectedDaysTextColor = connectedDays.textColor
                    day.connectedDaysSelectedTextColor = connectedDays.selectedTextColor
                    day.connectedDaysDisabledTextColor = connectedDays.disabledTextColor
                }
            }
        }
    }

}
