package com.loitpcore.views.calendar.cosmoCalendar.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.loitpcore.R
import com.loitpcore.views.calendar.cosmoCalendar.adapter.viewHolder.DayOfWeekHolder
import com.loitpcore.views.calendar.cosmoCalendar.model.Day
import com.loitpcore.views.calendar.cosmoCalendar.view.CalendarView

class DayOfWeekDelegate(
    calendarView: CalendarView
) : BaseDelegate() {

    init {
        this.calendarView = calendarView
    }

    fun onCreateDayHolder(parent: ViewGroup, viewType: Int): DayOfWeekHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_cosmo_calendar_day_of_week, parent, false)
        return DayOfWeekHolder(view, calendarView)
    }

    fun onBindDayHolder(day: Day, holder: DayOfWeekHolder, position: Int) {
        holder.bind(day)
    }
}
