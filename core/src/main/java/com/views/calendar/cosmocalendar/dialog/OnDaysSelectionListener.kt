package com.views.calendar.cosmocalendar.dialog

import com.views.calendar.cosmocalendar.model.Day

interface OnDaysSelectionListener {
    fun onDaysSelected(selectedDays: List<Day>)
}
