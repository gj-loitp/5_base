package com.loitp.views.calendar.cosmoCalendar.adapter.viewHolder

import android.view.View
import com.loitpcore.R
import com.loitp.views.calendar.cosmoCalendar.model.Day
import com.loitpcore.views.calendar.cosmoCalendar.utils.Constants
import com.loitpcore.views.calendar.cosmoCalendar.view.CalendarView
import java.text.SimpleDateFormat
import java.util.* // ktlint-disable no-wildcard-imports

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DayOfWeekHolder(
    itemView: View,
    calendarView: CalendarView?
) : BaseDayHolder(itemView, calendarView) {

    private val mDayOfWeekFormatter: SimpleDateFormat

    init {
        tvDay = itemView.findViewById(R.id.tv_day_name)
        mDayOfWeekFormatter = SimpleDateFormat(Constants.DAY_NAME_FORMAT, Locale.getDefault())
    }

    fun bind(day: Day) {
        tvDay.text = mDayOfWeekFormatter.format(day.calendar.time)
        tvDay.setTextColor(calendarView.weekDayTitleTextColor)
    }
}
