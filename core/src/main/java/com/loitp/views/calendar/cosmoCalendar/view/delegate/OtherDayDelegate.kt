package com.loitp.views.calendar.cosmoCalendar.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.loitpcore.R
import com.loitp.views.calendar.cosmoCalendar.adapter.viewHolder.OtherDayHolder
import com.loitp.views.calendar.cosmoCalendar.model.Day
import com.loitp.views.calendar.cosmoCalendar.view.CalendarView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class OtherDayDelegate(private val calendarView: CalendarView) {

    @Suppress("unused")
    fun onCreateDayHolder(
        parent: ViewGroup,
        viewType: Int
    ): OtherDayHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.l_v_cosmo_calendar_other_day, parent, false)
        return OtherDayHolder(view, calendarView)
    }

    @Suppress("unused")
    fun onBindDayHolder(
        day: Day,
        holder: OtherDayHolder,
        position: Int
    ) {
        holder.bind(day)
    }
}
