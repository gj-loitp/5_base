package com.loitpcore.views.calendar.cosmocalendar.selection

import com.loitpcore.views.calendar.cosmocalendar.model.Day

class NoneSelectionManager : BaseSelectionManager() {

    override fun toggleDay(day: Day) {}

    override fun isDaySelected(day: Day): Boolean {
        return false
    }

    override fun clearSelections() {}
}
