package com.loitpcore.views.calendar.cosmoCalendar.selection

import com.loitpcore.views.calendar.cosmoCalendar.model.Day

abstract class BaseSelectionManager {
    @JvmField
    protected var onDaySelectedListener: OnDaySelectedListener? = null
    abstract fun toggleDay(day: Day)
    abstract fun isDaySelected(day: Day): Boolean
    abstract fun clearSelections()
}
