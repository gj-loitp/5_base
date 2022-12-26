package com.loitp.views.cal.cosmoCalendar.adapter.viewHolder

import android.view.View
import com.loitp.views.cal.cosmoCalendar.model.Day
import com.loitp.views.cal.cosmoCalendar.view.CalendarView
import com.loitp.R

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
