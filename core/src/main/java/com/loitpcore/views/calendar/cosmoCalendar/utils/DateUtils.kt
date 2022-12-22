package com.loitpcore.views.calendar.cosmoCalendar.utils

import android.text.format.DateUtils
import com.loitp.views.calendar.cosmoCalendar.model.Day
import java.util.* // ktlint-disable no-wildcard-imports

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object DateUtils {

    fun getCalendar(
        date: Date
    ): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    @JvmStatic
    fun getCalendar(
        timeInMillis: Long
    ): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        return calendar
    }

    @JvmStatic
    fun getFirstDayOfMonth(
        date: Date
    ): Date {
        val calendar = getCalendar(date)
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        return calendar.time
    }

    @JvmStatic
    fun getLastDayOfMonth(
        date: Date
    ): Date {
        val calendar = getCalendar(date)
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        return calendar.time
    }

    @JvmStatic
    fun getFirstDayOfWeek(
        date: Date?,
        firstDayOfWeek: Int
    ): Date {
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        calendar.clear(Calendar.HOUR_OF_DAY)
        calendar.clear(Calendar.HOUR)
        while (calendar[Calendar.DAY_OF_WEEK] != firstDayOfWeek) {
            calendar.add(Calendar.DATE, -1)
        }
        return calendar.time
    }

    @JvmStatic
    fun getLastDayOfWeek(
        date: Date?
    ): Date {
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        calendar.clear(Calendar.HOUR_OF_DAY)
        calendar.clear(Calendar.HOUR)
        if (calendar[Calendar.DAY_OF_MONTH] == calendar.getActualMaximum(Calendar.DAY_OF_MONTH) &&
            calendar[Calendar.DAY_OF_WEEK] == Calendar.SUNDAY
        ) {
            return calendar.time
        }
        calendar[Calendar.DAY_OF_WEEK] = calendar.getActualMaximum(Calendar.DAY_OF_WEEK)
        while (calendar[Calendar.DAY_OF_WEEK] != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1)
        }
        return calendar.time
    }

    @JvmStatic
    fun isSameMonth(
        calendar1: Calendar,
        calendar2: Calendar
    ): Boolean {
        return calendar1[Calendar.MONTH] == calendar2[Calendar.MONTH]
    }

    @JvmStatic
    fun isSameDayOfMonth(
        calendar1: Calendar,
        calendar2: Calendar
    ): Boolean {
        return calendar1[Calendar.DAY_OF_MONTH] == calendar2[Calendar.DAY_OF_MONTH]
    }

    @JvmStatic
    fun addMonth(
        calendar: Calendar
    ): Calendar {
        calendar.add(Calendar.MONTH, 1)
        return calendar
    }

    @JvmStatic
    fun addDay(
        calendar: Calendar
    ): Calendar {
        calendar.add(Calendar.DATE, 1)
        return calendar
    }

    @JvmStatic
    fun isCurrentDate(
        date: Date?
    ): Boolean {
        return date != null && DateUtils.isToday(date.time)
    }

    @JvmStatic
    fun isDayInRange(
        day: Day,
        dayStart: Day,
        dayEnd: Day
    ): Boolean {
        val calendarStart = Calendar.getInstance()
        calendarStart.time = dayStart.calendar.time
        setCalendarToStartOfDay(calendarStart)
        val calendarEnd = Calendar.getInstance()
        calendarEnd.time = dayEnd.calendar.time
        setCalendarToEndOfDay(calendarEnd)
        return (
            day.calendar.timeInMillis >= calendarStart.timeInMillis &&
                day.calendar.timeInMillis <= calendarEnd.timeInMillis
            )
    }

    private fun setCalendarToStartOfDay(calendar: Calendar) {
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
    }

    private fun setCalendarToEndOfDay(calendar: Calendar) {
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 59
    }
}
