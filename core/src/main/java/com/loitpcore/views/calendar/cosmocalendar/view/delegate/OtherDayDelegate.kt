package com.loitpcore.views.calendar.cosmocalendar.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.loitpcore.R
import com.loitpcore.views.calendar.cosmocalendar.adapter.viewholder.OtherDayHolder
import com.loitpcore.views.calendar.cosmocalendar.model.Day
import com.loitpcore.views.calendar.cosmocalendar.view.CalendarView

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
