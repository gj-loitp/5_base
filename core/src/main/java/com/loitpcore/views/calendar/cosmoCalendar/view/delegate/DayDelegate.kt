package com.loitpcore.views.calendar.cosmoCalendar.view.delegate

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.R
import com.loitpcore.views.calendar.cosmoCalendar.adapter.MonthAdapter
import com.loitpcore.views.calendar.cosmoCalendar.adapter.viewHolder.DayHolder
import com.loitpcore.views.calendar.cosmoCalendar.model.Day
import com.loitpcore.views.calendar.cosmoCalendar.selection.MultipleSelectionManager
import com.loitpcore.views.calendar.cosmoCalendar.view.CalendarView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DayDelegate(
    calendarView: CalendarView?,
    monthAdapter: MonthAdapter
) : BaseDelegate() {

    private val monthAdapter: MonthAdapter

    init {
        this.calendarView = calendarView
        this.monthAdapter = monthAdapter
    }

    @Suppress("unused")
    fun onCreateDayHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_cosmo_calendar_day, parent, false)
        return DayHolder(view, calendarView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onBindDayHolder(
        daysAdapter: RecyclerView.Adapter<*>,
        day: Day,
        holder: DayHolder,
        position: Int
    ) {
        val selectionManager = monthAdapter.selectionManager
        holder.bind(day, selectionManager)
        holder.itemView.setOnClickListener {
            if (!day.isDisabled) {
                selectionManager.toggleDay(day)
                if (selectionManager is MultipleSelectionManager) {
                    daysAdapter.notifyItemChanged(position)
                } else {
                    monthAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}
