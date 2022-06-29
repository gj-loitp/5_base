package com.loitpcore.views.calendar.cosmocalendar.view.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import com.loitpcore.R
import com.loitpcore.views.calendar.cosmocalendar.adapter.DaysAdapter
import com.loitpcore.views.calendar.cosmocalendar.adapter.viewholder.MonthHolder
import com.loitpcore.views.calendar.cosmocalendar.model.Month
import com.loitpcore.views.calendar.cosmocalendar.settings.SettingsManager

class MonthDelegate(
    private val appearanceModel: SettingsManager
) {

    fun onCreateMonthHolder(adapter: DaysAdapter?, parent: ViewGroup, viewType: Int): MonthHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_cosmo_calendar_month, parent, false)
        val holder = MonthHolder(view, appearanceModel)
        holder.setDayAdapter(adapter)
        return holder
    }

    fun onBindMonthHolder(month: Month, holder: MonthHolder, position: Int) {
        holder.bind(month)
    }
}
