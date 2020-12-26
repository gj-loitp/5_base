package com.views.calendar.cosmocalendar.selection

import com.views.calendar.cosmocalendar.model.Day

class NoneSelectionManager : BaseSelectionManager() {

    override fun toggleDay(day: Day) {}

    override fun isDaySelected(day: Day): Boolean {
        return false
    }

    override fun clearSelections() {}
}
