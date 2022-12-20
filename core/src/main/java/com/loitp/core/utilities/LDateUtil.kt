package com.loitp.core.utilities

import com.loitp.core.common.Constants
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LDateUtil {

    companion object {

        val currentDate: String
            get() {
                val c = Calendar.getInstance()
                val df = SimpleDateFormat("ddMM_HHmm", Locale.getDefault())
                return df.format(c.time)
            }

        val currentYearMonth: String
            get() {
                val c = Calendar.getInstance()
                val df = SimpleDateFormat("yyyy-MM", Locale.getDefault())
                return df.format(c.time)
            }

        val currentMonth: String
            get() {
                val c = Calendar.getInstance()
                val df = SimpleDateFormat("MM", Locale.getDefault())
                return df.format(c.time)
            }

        fun now(): String? {
            val tz = TimeZone.getTimeZone("UTC")
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            df.timeZone = tz
            return df.format(Date())
        }

        fun convertFormatDate(
            strDate: String?,
            fromFormat: String,
            toFormat: String,
        ): String? {
            if (strDate.isNullOrEmpty()) {
                return null
            }
            val date = stringToDate(text = strDate, format = fromFormat)
            date?.let {
                val simpleDateFormat = SimpleDateFormat(toFormat, Locale.ENGLISH)
                // simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                return simpleDateFormat.format(it)
            }
            return null
        }

        fun stringToDate(
            text: String,
            format: String
        ): Date? {
            val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
            return try {
                dateFormat.parse(text)
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }
        }

        fun dateToString(
            date: Date,
            format: String
        ): String? {
            val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
            return try {
                dateFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun getDate(
            year: Int,
            month: Int,
            day: Int
        ): Date {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            return calendar.time
        }

        fun formatDatePicker(
            year: Int,
            month: Int,
            day: Int,
            format: String
        ): String {
            val cal = Calendar.getInstance()
            cal.timeInMillis = 0
            cal.set(year, month, day)
            val date = cal.time
            // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            return sdf.format(date)
        }

        fun getTime(
            hr: Int,
            min: Int
        ): String {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hr)
            cal.set(Calendar.MINUTE, min)
            val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
            return formatter.format(cal.time)
        }

        fun getDateWithoutTime(dateString: String): String? {
            return getDate(dateString, "dd/MM/yyyy")
        }

        @JvmOverloads
        fun getDate(
            dateString: String,
            format: String = "dd/MM/yyyy hh:mm aa"
        ): String? {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            return try {
                val value = formatter.parse(dateString)
                val dateFormatter = SimpleDateFormat(format, Locale.ENGLISH)
                dateFormatter.timeZone = TimeZone.getDefault()
                if (value == null) {
                    return null
                }
                dateFormatter.format(value)
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }
        }

        // date ex: 14-09-2017
        fun convertDateToTimestamp(d: String): Long {
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date: Date?
            return try {
                date = formatter.parse(d) as Date
                date.time / 1000
            } catch (e: ParseException) {
                e.printStackTrace()
                Constants.NOT_FOUND.toLong()
            }
        }

        fun zeroTime(date: Date): Date {
            return setTime(date = date, hourOfDay = 0, minute = 0, second = 0, ms = 0)
        }

        fun setTime(
            date: Date,
            hourOfDay: Int,
            minute: Int,
            second: Int,
            ms: Int
        ): Date {
            val gc = GregorianCalendar()
            gc.time = date
            gc.set(Calendar.HOUR_OF_DAY, hourOfDay)
            gc.set(Calendar.MINUTE, minute)
            gc.set(Calendar.SECOND, second)
            gc.set(Calendar.MILLISECOND, ms)
            return gc.time
        }

        fun convertDateToTimeStamp(datetime: String): Long {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date?
            return try {
                date = dateFormat.parse(datetime)
                val time = date!!.time
                time
            } catch (e: ParseException) {
                e.printStackTrace()
                0
            }
        }

        @Suppress("unused")
        fun getDateFromDateTime(datetime: String): String {
            val date = datetime.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return date[0]
        }

        fun convertStringToCalendar(yyyymmdd: String): Calendar? {
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val cal = Calendar.getInstance()
            try {
                val date = df.parse(yyyymmdd) ?: return null
                cal.time = date
                return cal
            } catch (e: ParseException) {
                e.printStackTrace()
                return null
            }
        }

        fun convertStringDate(
            yyyymmdd: String,
            format: String
        ): Calendar? {
            val df = SimpleDateFormat(format, Locale.getDefault())
            val cal = Calendar.getInstance()
            val date: Date?
            try {
                date = df.parse(yyyymmdd) ?: return null
                cal.time = date
                return cal
            } catch (e: ParseException) {
                e.printStackTrace()
                return null
            }
        }

        /*
         * getDateCurrentTimeZone("1524141369", "yyyy-MM-dd HH:mm:ss");
         * -> 2018-04-20 02:36:09
         */
        fun getDateCurrentTimeZone(
            timestamp: Long,
            format: String
        ): String {
            return try {
                val calendar = Calendar.getInstance()
                // TimeZone tz = TimeZone.getDefault();
                val tz = TimeZone.getTimeZone("UTC")
                calendar.timeInMillis = timestamp * 1000
                calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                val currentTimeZone = calendar.time as Date
                sdf.format(currentTimeZone)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        /*
         * getDateCurrentTimeZone(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
         * -> 2018-04-20 02:36:09
         */
        fun getDateCurrentTimeZoneMls(
            timestampMls: Long,
            format: String
        ): String {
            return try {
                val calendar = Calendar.getInstance()
                // TimeZone tz = TimeZone.getDefault();
                val tz = TimeZone.getTimeZone("UTC")
                calendar.timeInMillis = timestampMls
                calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                val currentTimeZone = calendar.time as Date
                sdf.format(currentTimeZone)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        @Suppress("unused")
        fun convertDate(
            dateInMilliseconds: String,
            dateFormat: String
        ): String? {
            return try {
                val timeStamp = java.lang.Long.parseLong(dateInMilliseconds)
                val objFormatter = SimpleDateFormat(dateFormat, Locale.getDefault())
                objFormatter.timeZone = TimeZone.getDefault()
                val objCalendar = Calendar.getInstance(TimeZone.getDefault())
                objCalendar.timeInMillis = timeStamp * 1000
                val result = objFormatter.format(objCalendar.time)
                objCalendar.clear()
                result
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        @Suppress("unused")
        fun convertSToFormat(
            second: Long,
            format: String
        ): String? {
            val d = Date(second * 1000L)
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.timeZone = TimeZone.getTimeZone("GMT")
            return df.format(d)
        }
    }
}
