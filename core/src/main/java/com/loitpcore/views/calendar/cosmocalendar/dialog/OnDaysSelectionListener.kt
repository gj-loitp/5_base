package com.loitpcore.views.calendar.cosmocalendar.dialog

import com.loitpcore.views.calendar.cosmocalendar.model.Day

interface OnDaysSelectionListener {
    fun onDaysSelected(selectedDays: List<Day>)
}
