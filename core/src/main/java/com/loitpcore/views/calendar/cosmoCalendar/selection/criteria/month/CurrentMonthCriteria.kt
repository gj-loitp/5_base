package com.loitpcore.views.calendar.cosmoCalendar.selection.criteria.month

import com.loitpcore.views.calendar.cosmoCalendar.utils.DateUtils.getCalendar
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