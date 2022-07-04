package com.loitpcore.views.calendar.cosmoCalendar.selection

import com.loitpcore.views.calendar.cosmoCalendar.model.Day
import com.loitpcore.views.calendar.cosmoCalendar.selection.criteria.BaseCriteria
import java.util.*

class MultipleSelectionManager : BaseCriteriaSelectionManager {
    private val days: MutableSet<Day> = HashSet()

    constructor(onDaySelectedListener: OnDaySelectedListener?) {
        this.onDaySelectedListener = onDaySelectedListener
    }

    constructor(criteria: BaseCriteria?, onDaySelectedListener: OnDaySelectedListener?) : this(
        ArrayList<BaseCriteria>(setOf(criteria)),
        onDaySelectedListener
    )

    constructor(
        criteriaList: ArrayList<BaseCriteria>,
        onDaySelectedListener: OnDaySelectedListener?
    ) {
        this.criteriaList = criteriaList
        this.onDaySelectedListener = onDaySelectedListener
    }

    override fun toggleDay(day: Day) {
        if (days.contains(day)) {
            days.remove(day)
        } else {
            days.add(day)
        }
        onDaySelectedListener?.onDaySelected()
    }

    override fun isDaySelected(day: Day): Boolean {
        return days.contains(day) || isDaySelectedByCriteria(day = day)
    }

    override fun clearSelections() {
        days.clear()
    }

    fun removeDay(day: Day) {
        days.remove(day)
        onDaySelectedListener?.onDaySelected()
    }
}
