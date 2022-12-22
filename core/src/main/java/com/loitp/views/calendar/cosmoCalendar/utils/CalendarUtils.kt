package com.loitp.views.calendar.cosmoCalendar.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.view.WindowManager
import com.loitp.views.calendar.cosmoCalendar.model.Day
import com.loitp.views.calendar.cosmoCalendar.model.DayOfWeek
import com.loitp.views.calendar.cosmoCalendar.model.Month
import com.loitp.views.calendar.cosmoCalendar.selection.selectionBar.SelectionBarContentItem
import com.loitp.views.calendar.cosmoCalendar.selection.selectionBar.SelectionBarItem
import com.loitp.views.calendar.cosmoCalendar.selection.selectionBar.SelectionBarTitleItem
import com.loitp.views.calendar.cosmoCalendar.settings.SettingsManager
import com.loitp.views.calendar.cosmoCalendar.settings.lists.DisabledDaysCriteria
import com.loitp.views.calendar.cosmoCalendar.settings.lists.DisabledDaysCriteriaType
import com.loitp.views.calendar.cosmoCalendar.utils.DateUtils.addDay
import com.loitp.views.calendar.cosmoCalendar.utils.DateUtils.addMonth
import com.loitp.views.calendar.cosmoCalendar.utils.DateUtils.getCalendar
import com.loitp.views.calendar.cosmoCalendar.utils.DateUtils.getFirstDayOfMonth
import com.loitp.views.calendar.cosmoCalendar.utils.DateUtils.getFirstDayOfWeek
import com.loitp.views.calendar.cosmoCalendar.utils.DateUtils.getLastDayOfMonth
import com.loitp.views.calendar.cosmoCalendar.utils.DateUtils.getLastDayOfWeek
import com.loitp.views.calendar.cosmoCalendar.utils.DateUtils.isSameDayOfMonth
import com.loitp.views.calendar.cosmoCalendar.utils.DateUtils.isSameMonth
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object CalendarUtils {

    fun createMonth(
        date: Date,
        settingsManager: SettingsManager
    ): Month {
        val days: MutableList<Day> = ArrayList()
        val firstDisplayedDayCalendar = Calendar.getInstance()
        val firstDayOfMonthCalendar = Calendar.getInstance()

        // First day that belongs to month
        val firstDayOfMonth = getFirstDayOfMonth(date)
        firstDayOfMonthCalendar.time = firstDayOfMonth
        firstDayOfMonthCalendar[Calendar.MONTH]
        val targetMonth = firstDayOfMonthCalendar[Calendar.MONTH]

        // First displayed day, can belong to previous month
        val firstDisplayedDay = getFirstDayOfWeek(firstDayOfMonth, settingsManager.firstDayOfWeek)
        firstDisplayedDayCalendar.time = firstDisplayedDay
        val end = Calendar.getInstance()
        end.time = getLastDayOfWeek(getLastDayOfMonth(date))

        // Create week day titles
        if (settingsManager.isShowDaysOfWeek) {
            days.addAll(createDaysOfWeek(firstDisplayedDay))
        }

        // Create first day of month
        days.add(createDay(firstDisplayedDayCalendar, settingsManager, targetMonth))

        // Create other days in month
        do {
            addDay(firstDisplayedDayCalendar)
            days.add(createDay(firstDisplayedDayCalendar, settingsManager, targetMonth))
        } while (!isSameDayOfMonth(firstDisplayedDayCalendar, end) || !isSameMonth(
                firstDisplayedDayCalendar,
                end
            )
        )
        return Month(createDay(firstDayOfMonthCalendar, settingsManager, targetMonth), days)
    }

    private fun createDay(
        calendar: Calendar,
        settingsManager: SettingsManager,
        targetMonth: Int
    ): Day {
        val day = Day(calendar)
        day.isBelongToMonth = calendar[Calendar.MONTH] == targetMonth
        setDay(day, settingsManager)
        return day
    }

    private fun createDaysOfWeek(
        firstDisplayedDay: Date
    ): List<DayOfWeek> {
        val daysOfTheWeek: MutableList<DayOfWeek> = ArrayList()
        val calendar = getCalendar(firstDisplayedDay)
        val startDayOfTheWeek = calendar[Calendar.DAY_OF_WEEK]
        do {
            daysOfTheWeek.add(DayOfWeek(calendar.time))
            addDay(calendar)
        } while (calendar[Calendar.DAY_OF_WEEK] != startDayOfTheWeek)
        return daysOfTheWeek
    }

    @JvmStatic
    fun createWeekDayTitles(
        firstDayOfWeek: Int
    ): List<String> {
        val sdf = SimpleDateFormat(Constants.DAY_NAME_FORMAT, Locale.getDefault())
        val titles: MutableList<String> = ArrayList()
        val calendar = Calendar.getInstance()
        calendar[Calendar.DAY_OF_WEEK] = firstDayOfWeek
        do {
            titles.add(sdf.format(calendar.time))
            addDay(calendar)
        } while (calendar[Calendar.DAY_OF_WEEK] != firstDayOfWeek)
        return titles
    }

    @JvmStatic
    fun createInitialMonths(
        settingsManager: SettingsManager
    ): List<Month> {
        val months: MutableList<Month> = ArrayList()
        val calendar = Calendar.getInstance()
        for (i in 0 until SettingsManager.DEFAULT_MONTH_COUNT / 2) {
            calendar.add(Calendar.MONTH, -1)
        }
        for (i in 0 until SettingsManager.DEFAULT_MONTH_COUNT) {
            months.add(createMonth(calendar.time, settingsManager))
            addMonth(calendar)
        }
        return months
    }

    /**
     * Returns selected Days grouped by month/year
     */
    @JvmStatic
    fun getSelectedDayListForMultipleMode(
        selectedDays: List<Day>
    ): List<SelectionBarItem> {
        val result: MutableList<SelectionBarItem> = ArrayList()
        val tempCalendar = Calendar.getInstance()
        var tempYear = -1
        var tempMonth = -1
        for (day in selectedDays) {
            tempCalendar.time = day.calendar.time
            if (tempCalendar[Calendar.YEAR] != tempYear || tempCalendar[Calendar.MONTH] != tempMonth) {
                result.add(SelectionBarTitleItem(getYearNameTitle(day)))
                tempYear = tempCalendar[Calendar.YEAR]
                tempMonth = tempCalendar[Calendar.MONTH]
            }
            result.add(SelectionBarContentItem(day))
        }
        return result
    }

    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun getYearNameTitle(
        day: Day
    ): String {
        return SimpleDateFormat("MMM''yy").format(day.calendar.time)
    }

    /**
     * Returns width of circle
     */
    @JvmStatic
    fun getCircleWidth(
        context: Context
    ): Int {
        return getDisplayWidth(context) / Constants.DAYS_IN_WEEK
    }

    private fun getDisplayWidth(
        context: Context
    ): Int {
        return (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.width
    }

    /**
     * Sets variables(isWeekend, isDisabled, isFromConnectedCalendar) to day
     */
    private fun setDay(
        day: Day,
        settingsManager: SettingsManager
    ) {
        if (settingsManager.weekendDays != null) {
            val dayOfWeek: Long = day.calendar[Calendar.DAY_OF_WEEK].toLong()
            day.isWeekend = settingsManager.weekendDays.contains(dayOfWeek)
        }
        if (settingsManager.minDate != null) {
            day.isDisabled = isDayDisabledByMinDate(day, settingsManager.minDate)
        }
        if (settingsManager.maxDate != null) {
            if (!day.isDisabled) {
                day.isDisabled = isDayDisabledByMaxDate(day, settingsManager.maxDate)
            }
        }
        if (settingsManager.disabledDays != null) {
            // day.setDisabled(isDayInSet(day, settingsManager.getDisabledDays()));
            if (!day.isDisabled) {
                day.isDisabled = isDayInSet(day, settingsManager.disabledDays)
            }
        }
        if (settingsManager.disabledDaysCriteria != null) {
            if (!day.isDisabled) {
                day.isDisabled = isDayDisabledByCriteria(day, settingsManager.disabledDaysCriteria)
            }
        }
        if (settingsManager.connectedDaysManager.isAnyConnectedDays) {
            settingsManager.connectedDaysManager.applySettingsToDay(day)
        }
    }

    @JvmStatic
    fun isDayInSet(
        day: Day,
        daysInSet: Set<Long>
    ): Boolean {
        for (disabledTime in daysInSet) {
            val disabledDayCalendar = getCalendar(disabledTime)
            if (day.calendar[Calendar.YEAR] == disabledDayCalendar[Calendar.YEAR] &&
                day.calendar[Calendar.DAY_OF_YEAR] == disabledDayCalendar[Calendar.DAY_OF_YEAR]
            ) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun isDayDisabledByMinDate(
        day: Day,
        minDate: Calendar
    ): Boolean {
        return (
            day.calendar[Calendar.YEAR] < minDate[Calendar.YEAR] ||
                day.calendar[Calendar.YEAR] == minDate[Calendar.YEAR] &&
                day.calendar[Calendar.DAY_OF_YEAR] < minDate[Calendar.DAY_OF_YEAR]
            )
    }

    @JvmStatic
    fun isDayDisabledByMaxDate(
        day: Day,
        maxDate: Calendar
    ): Boolean {
        return (
            day.calendar[Calendar.YEAR] > maxDate[Calendar.YEAR] ||
                day.calendar[Calendar.YEAR] == maxDate[Calendar.YEAR] &&
                day.calendar[Calendar.DAY_OF_YEAR] > maxDate[Calendar.DAY_OF_YEAR]
            )
    }

    @JvmStatic
    fun isDayDisabledByCriteria(
        day: Day,
        criteria: DisabledDaysCriteria
    ): Boolean {
        val field: Int = when (criteria.criteriaType) {
            DisabledDaysCriteriaType.DAYS_OF_MONTH -> Calendar.DAY_OF_MONTH
            DisabledDaysCriteriaType.DAYS_OF_WEEK -> Calendar.DAY_OF_WEEK
        }
        criteria.days?.forEach { dayInt ->
            if (dayInt == day.calendar[field]) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun getIconHeight(
        resources: Resources?,
        iconResId: Int
    ): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, iconResId, options)
        return options.outHeight
    }
}
