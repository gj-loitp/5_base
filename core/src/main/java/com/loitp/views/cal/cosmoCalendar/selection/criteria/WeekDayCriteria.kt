package com.loitp.views.cal.cosmoCalendar.selection.criteria

import com.loitp.views.cal.cosmoCalendar.model.Day
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class WeekDayCriteria(
    weekDay: Int
) : BaseCriteria() {
    private var weekDay = -1

    init {
        if (weekDay in 1..7) {
            this.weekDay = weekDay
        } else {
            throw IllegalArgumentException("Weekday must be from 1 to 7")
        }
    }

    override fun isCriteriaPassed(day: Day): Boolean {
        return day.calendar[Calendar.DAY_OF_WEEK] == weekDay
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as WeekDayCriteria
        return weekDay == that.weekDay
    }

    override fun hashCode(): Int {
        return weekDay
    }
}
