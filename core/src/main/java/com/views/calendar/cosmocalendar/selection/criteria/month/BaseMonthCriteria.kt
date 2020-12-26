package com.views.calendar.cosmocalendar.selection.criteria.month

import com.views.calendar.cosmocalendar.model.Day
import com.views.calendar.cosmocalendar.selection.criteria.BaseCriteria
import java.util.*

abstract class BaseMonthCriteria : BaseCriteria() {

    protected abstract fun getMonth(): Int
    protected abstract fun getYear(): Int

    override fun isCriteriaPassed(day: Day): Boolean {
        return (day.calendar[Calendar.MONTH] == getMonth()
                && day.calendar[Calendar.YEAR] == getYear())
    }
}
