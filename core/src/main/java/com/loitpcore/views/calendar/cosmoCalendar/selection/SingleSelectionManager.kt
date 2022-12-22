package com.loitpcore.views.calendar.cosmoCalendar.selection

import com.loitp.views.calendar.cosmoCalendar.model.Day

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SingleSelectionManager(
    onDaySelectedListener: OnDaySelectedListener?
) : BaseSelectionManager() {

    init {
        this.onDaySelectedListener = onDaySelectedListener
    }

    private var day: Day? = null

    override fun toggleDay(day: Day) {
        this.day = day
        onDaySelectedListener?.onDaySelected()
    }

    override fun isDaySelected(day: Day): Boolean {
        return day == this.day
    }

    override fun clearSelections() {
        day = null
    }
}
