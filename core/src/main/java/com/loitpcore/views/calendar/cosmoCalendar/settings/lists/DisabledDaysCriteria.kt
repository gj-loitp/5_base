package com.loitpcore.views.calendar.cosmoCalendar.settings.lists

import java.util.*

class DisabledDaysCriteria {

    companion object {
        private const val MAX_DAYS_COUNT_IN_MONTH = 31
        private const val MAX_DAYS_COUNT_IN_WEEK = 7
    }

    var criteriaType = DisabledDaysCriteriaType.DAYS_OF_MONTH
        private set

    var days: Set<Int>? = null
        private set

    constructor(startRange: Int, endRange: Int, criteriaType: DisabledDaysCriteriaType) {
        setDays(startRange, endRange, criteriaType)
    }

    constructor(days: Set<Int>, criteriaType: DisabledDaysCriteriaType) {
        setDays(days, criteriaType)
    }

    fun setDays(days: Set<Int>, criteriaType: DisabledDaysCriteriaType) {
        this.criteriaType = criteriaType
        validateDays(days)
        this.days = days
    }

    /**
     * Sets range of disabled days
     */
    fun setDays(
        startRange: Int,
        endRange: Int,
        criteriaType: DisabledDaysCriteriaType
    ) {
        require(!(criteriaType === DisabledDaysCriteriaType.DAYS_OF_MONTH && startRange >= endRange)) { "startRange must be less than endRange" }
        require(startRange >= 1) { "startRange must be more than 0" }
        require(endRange >= 1) { "endRange must be more than 0" }
        this.criteriaType = criteriaType
        val days: MutableSet<Int> = TreeSet()
        val start: Int
        val end: Int
        if (startRange >= endRange) {
            start = endRange
            end = startRange
        } else {
            start = startRange
            end = endRange
        }
        for (i in start until end + 1) {
            days.add(i)
        }
        validateDays(days)
        this.days = days
    }

    private fun validateDays(days: Set<Int>) {
        val maxPossibleValue: Int = if (criteriaType === DisabledDaysCriteriaType.DAYS_OF_MONTH) {
            MAX_DAYS_COUNT_IN_MONTH
        } else {
            MAX_DAYS_COUNT_IN_WEEK
        }
        for (day in days) {
            require(day <= maxPossibleValue) { "Invalid day:$day" }
        }
    }
}
