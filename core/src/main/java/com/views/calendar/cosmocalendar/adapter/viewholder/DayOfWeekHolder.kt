package com.views.calendar.cosmocalendar.adapter.viewholder

import android.view.View
import com.R
import com.views.calendar.cosmocalendar.model.Day
import com.views.calendar.cosmocalendar.utils.Constants
import com.views.calendar.cosmocalendar.view.CalendarView
import java.text.SimpleDateFormat
import java.util.*

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
