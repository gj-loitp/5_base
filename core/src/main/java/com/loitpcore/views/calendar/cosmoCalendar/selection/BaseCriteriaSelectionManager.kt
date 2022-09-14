package com.loitpcore.views.calendar.cosmoCalendar.selection

import com.loitpcore.views.calendar.cosmoCalendar.model.Day
import com.loitpcore.views.calendar.cosmoCalendar.selection.criteria.BaseCriteria

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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
        criteriaList.removeAll(listToDelete.toSet())
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
