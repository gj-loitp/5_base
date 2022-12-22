package com.loitp.views.calendar.cosmoCalendar.selection.criteria.month

import com.loitpcore.views.calendar.cosmoCalendar.utils.DateUtils.getCalendar
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class NextMonthCriteria : BaseMonthCriteria() {

    private val currentTimeInMillis: Long = System.currentTimeMillis()

    override fun getMonth(): Int {
        val calendar = getCalendar(currentTimeInMillis)
        calendar.add(Calendar.MONTH, 1)
        return calendar[Calendar.MONTH]
    }

    override fun getYear(): Int {
        val calendar = getCalendar(currentTimeInMillis)
        calendar.add(Calendar.MONTH, 1)
        return calendar[Calendar.YEAR]
    }

}
