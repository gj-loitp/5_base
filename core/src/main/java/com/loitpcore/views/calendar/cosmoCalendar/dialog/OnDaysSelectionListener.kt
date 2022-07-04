package com.loitpcore.views.calendar.cosmoCalendar.dialog

import com.loitpcore.views.calendar.cosmoCalendar.model.Day

interface OnDaysSelectionListener {
    fun onDaysSelected(selectedDays: List<Day>)
}
