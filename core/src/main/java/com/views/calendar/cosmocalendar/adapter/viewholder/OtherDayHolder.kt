package com.views.calendar.cosmocalendar.adapter.viewholder

import android.view.View
import com.R
import com.views.calendar.cosmocalendar.model.Day
import com.views.calendar.cosmocalendar.view.CalendarView

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
