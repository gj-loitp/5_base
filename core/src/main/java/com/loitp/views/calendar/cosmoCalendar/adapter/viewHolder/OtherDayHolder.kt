package com.loitp.views.calendar.cosmoCalendar.adapter.viewHolder

import android.view.View
import com.loitpcore.R
import com.loitp.views.calendar.cosmoCalendar.model.Day
import com.loitpcore.views.calendar.cosmoCalendar.view.CalendarView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class OtherDayHolder(
    itemView: View,
    calendarView: CalendarView
) : BaseDayHolder(itemView, calendarView) {

    init {
        tvDay = itemView.findViewById(R.id.tv_day_number)
    }

    fun bind(day: Day) {
        tvDay.text = day.dayNumber.toString()
        tvDay.setTextColor(calendarView.otherDayTextColor)
    }
}
