package com.loitpcore.views.calendar.cosmoCalendar.selection

import com.loitp.views.calendar.cosmoCalendar.model.Day

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class BaseSelectionManager {
    @JvmField
    protected var onDaySelectedListener: OnDaySelectedListener? = null
    abstract fun toggleDay(day: Day)
    abstract fun isDaySelected(day: Day): Boolean
    abstract fun clearSelections()
}
