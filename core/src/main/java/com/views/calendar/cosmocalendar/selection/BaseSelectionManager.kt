package com.views.calendar.cosmocalendar.selection

import com.views.calendar.cosmocalendar.model.Day

abstract class BaseSelectionManager {
    @JvmField
    protected var onDaySelectedListener: OnDaySelectedListener? = null
    abstract fun toggleDay(day: Day)
    abstract fun isDaySelected(day: Day): Boolean
    abstract fun clearSelections()
}
