package com.views.calendar.cosmocalendar.selection.criteria.month

import com.views.calendar.cosmocalendar.utils.DateUtils.getCalendar
import java.util.*

class CurrentMonthCriteria : BaseMonthCriteria() {

    private val currentTimeInMillis: Long = System.currentTimeMillis()

    override fun getMonth(): Int {
        return getCalendar(currentTimeInMillis)[Calendar.MONTH]
    }

    override fun getYear(): Int {
        return getCalendar(currentTimeInMillis)[Calendar.YEAR]
    }

}
