package com.views.calendar.cosmocalendar.selection

import android.util.Pair
import com.views.calendar.cosmocalendar.model.Day
import com.views.calendar.cosmocalendar.utils.DateUtils.isDayInRange

class RangeSelectionManager(
        onDaySelectedListener: OnDaySelectedListener?
) : BaseSelectionManager() {

    var days: Pair<Day, Day>? = null
        private set
    private var tempDay: Day? = null

    init {
        this.onDaySelectedListener = onDaySelectedListener
    }

    override fun toggleDay(day: Day) {
        if (tempDay == null) {
            tempDay = day
            days = null
        } else {
            if (tempDay === day) {
                return
            }
            tempDay?.let {
                days = if (it.calendar.time.before(day.calendar.time)) {
                    Pair.create(it, day)
                } else {
                    Pair.create(day, it)
                }
            }
            tempDay = null
        }
        onDaySelectedListener?.onDaySelected()
    }

    override fun isDaySelected(day: Day): Boolean {
        return isDaySelectedManually(day)
    }

    private fun isDaySelectedManually(day: Day): Boolean {
        return when {
            tempDay != null -> {
                day == tempDay
            }
            days != null -> {
//                isDayInRange(day, days!!.first, days!!.second)
                days?.let {
                    return isDayInRange(day, it.first, it.second)
                }
                return false
            }
            else -> {
                false
            }
        }
    }

    override fun clearSelections() {
        days = null
        tempDay = null
    }

    fun getSelectedState(day: Day): SelectionState {
        if (!isDaySelectedManually(day)) {
            return SelectionState.SINGLE_DAY
        }
        return when {
            days == null -> {
                SelectionState.START_RANGE_DAY_WITHOUT_END
            }
            days?.first == day -> {
                SelectionState.START_RANGE_DAY
            }
            days?.second == day -> {
                SelectionState.END_RANGE_DAY
            }
//            isDayInRange(day, days!!.first, days!!.second) -> {
//                SelectionState.RANGE_DAY
//            }
            else -> {
                if (days != null && isDayInRange(day, days!!.first, days!!.second)) {
                    SelectionState.RANGE_DAY
                } else {
                    SelectionState.SINGLE_DAY
                }
            }
        }
    }
}
