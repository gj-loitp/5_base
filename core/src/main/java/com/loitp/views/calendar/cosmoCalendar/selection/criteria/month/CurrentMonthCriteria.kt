package com.loitp.views.calendar.cosmoCalendar.selection.criteria.month

import com.loitp.views.calendar.cosmoCalendar.utils.DateUtils.getCalendar
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class CurrentMonthCriteria : BaseMonthCriteria() {

    private val currentTimeInMillis: Long = System.currentTimeMillis()

    override fun getMonth(): Int {
        return getCalendar(currentTimeInMillis)[Calendar.MONTH]
    }

    override fun getYear(): Int {
        return getCalendar(currentTimeInMillis)[Calendar.YEAR]
    }

}
