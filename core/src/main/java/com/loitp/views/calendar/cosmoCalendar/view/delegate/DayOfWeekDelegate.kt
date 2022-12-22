package com.loitp.views.calendar.cosmoCalendar.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.loitp.R
import com.loitp.views.calendar.cosmoCalendar.adapter.viewHolder.DayOfWeekHolder
import com.loitp.views.calendar.cosmoCalendar.model.Day
import com.loitp.views.calendar.cosmoCalendar.view.CalendarView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DayOfWeekDelegate(
    calendarView: CalendarView
) : BaseDelegate() {

    init {
        this.calendarView = calendarView
    }

    @Suppress("unused")
    fun onCreateDayHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayOfWeekHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.l_v_cosmo_calendar_day_of_week, parent, false)
        return DayOfWeekHolder(view, calendarView)
    }

    @Suppress("unused")
    fun onBindDayHolder(
        day: Day,
        holder: DayOfWeekHolder,
        position: Int
    ) {
        holder.bind(day)
    }
}
