package com.loitpcore.views.calendar.cosmocalendar.selection

import com.loitpcore.views.calendar.cosmocalendar.model.Day
import com.loitpcore.views.calendar.cosmocalendar.selection.criteria.BaseCriteria

abstract class BaseCriteriaSelectionManager : BaseSelectionManager() {
    @JvmField
    protected var criteriaList = ArrayList<BaseCriteria>()

    fun clearCriteriaList() {
        criteriaList.clear()
        notifyCriteriaUpdates()
    }

    fun addCriteriaList(criteriaList: List<BaseCriteria>) {
        this.criteriaList.addAll(criteriaList)
        notifyCriteriaUpdates()
    }

    fun addCriteria(criteria: BaseCriteria) {
        criteriaList.add(criteria)
        notifyCriteriaUpdates()
    }

    fun removeCriteria(criteria: BaseCriteria) {
        criteriaList.remove(criteria)
        notifyCriteriaUpdates()
    }

    fun removeCriteriaList(listToDelete: List<BaseCriteria>) {
        criteriaList.removeAll(listToDelete)
        notifyCriteriaUpdates()
    }

    private fun notifyCriteriaUpdates() {
        onDaySelectedListener?.onDaySelected()
    }

    private fun hasCriteria(): Boolean {
        return criteriaList.isNotEmpty()
    }

    fun isDaySelectedByCriteria(day: Day?): Boolean {
        if (hasCriteria()) {
            for (criteria in criteriaList) {
                if (criteria.isCriteriaPassed(day)) {
                    return true
                }
            }
        }
        return false
    }
}
