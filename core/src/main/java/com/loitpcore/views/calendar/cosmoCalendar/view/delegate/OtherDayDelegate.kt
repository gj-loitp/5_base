package com.loitpcore.views.calendar.cosmoCalendar.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.loitpcore.R
import com.loitpcore.views.calendar.cosmoCalendar.adapter.viewHolder.OtherDayHolder
import com.loitpcore.views.calendar.cosmoCalendar.model.Day
import com.loitpcore.views.calendar.cosmoCalendar.view.CalendarView

class OtherDayDelegate(private val calendarView: CalendarView) {
    fun onCreateDayHolder(parent: ViewGroup, viewType: Int): OtherDayHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_cosmo_calendar_other_day, parent, false)
        return OtherDayHolder(view, calendarView)
    }

    fun onBindDayHolder(day: Day, holder: OtherDayHolder, position: Int) {
        holder.bind(day)
    }
}
