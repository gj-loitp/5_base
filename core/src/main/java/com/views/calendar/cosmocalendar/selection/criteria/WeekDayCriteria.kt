package com.views.calendar.cosmocalendar.selection.criteria

import com.views.calendar.cosmocalendar.model.Day
import java.util.*

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

    override fun equals(any: Any?): Boolean {
        if (this === any) return true
        if (any == null || javaClass != any.javaClass) return false
        val that = any as WeekDayCriteria
        return weekDay == that.weekDay
    }

    override fun hashCode(): Int {
        return weekDay
    }

}
