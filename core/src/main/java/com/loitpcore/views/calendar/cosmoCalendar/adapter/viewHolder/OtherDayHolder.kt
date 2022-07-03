package com.loitpcore.views.calendar.cosmoCalendar.adapter.viewHolder

import android.view.View
import com.loitpcore.R
import com.loitpcore.views.calendar.cosmoCalendar.model.Day
import com.loitpcore.views.calendar.cosmoCalendar.view.CalendarView

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
