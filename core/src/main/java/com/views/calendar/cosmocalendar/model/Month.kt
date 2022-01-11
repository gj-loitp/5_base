package com.views.calendar.cosmocalendar.model

import androidx.annotation.Keep
import java.text.SimpleDateFormat
import java.util.*

@Keep
class Month(
    var firstDay: Day,
    val days: List<Day>
) {

    /**
     * Returns selected days that belong only to current month
     */
    val daysWithoutTitlesAndOnlyCurrent: List<Day>
        get() {
            val calendar = Calendar.getInstance()
            calendar.time = firstDay.calendar.time
            val currentMonth = calendar[Calendar.MONTH]
            val result: MutableList<Day> = ArrayList()
            for (day in days) {
                calendar.time = day.calendar.time
                if (day !is DayOfWeek && calendar[Calendar.MONTH] == currentMonth) {
                    result.add(day)
                }
            }
            return result
        }

    val monthName: String
        get() = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(firstDay.calendar.time)
}
