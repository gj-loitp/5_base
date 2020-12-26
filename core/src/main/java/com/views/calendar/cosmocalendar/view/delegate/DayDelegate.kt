package com.views.calendar.cosmocalendar.view.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.views.calendar.cosmocalendar.adapter.MonthAdapter
import com.views.calendar.cosmocalendar.adapter.viewholder.DayHolder
import com.views.calendar.cosmocalendar.model.Day
import com.views.calendar.cosmocalendar.selection.MultipleSelectionManager
import com.views.calendar.cosmocalendar.view.CalendarView

class DayDelegate(
        calendarView: CalendarView?,
        monthAdapter: MonthAdapter
) : BaseDelegate() {

    private val monthAdapter: MonthAdapter

    init {
        this.calendarView = calendarView
        this.monthAdapter = monthAdapter
    }

    fun onCreateDayHolder(parent: ViewGroup, viewType: Int): DayHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_cosmo_calendar_day, parent, false)
        return DayHolder(view, calendarView)
    }

    fun onBindDayHolder(
            daysAdapter: RecyclerView.Adapter<*>, day: Day,
            holder: DayHolder, position: Int
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
