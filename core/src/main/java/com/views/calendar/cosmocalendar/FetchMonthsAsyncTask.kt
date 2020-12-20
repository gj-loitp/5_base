package com.views.calendar.cosmocalendar

import android.os.AsyncTask
import com.views.calendar.cosmocalendar.FetchMonthsAsyncTask.FetchParams
import com.views.calendar.cosmocalendar.adapter.MonthAdapter
import com.views.calendar.cosmocalendar.model.Month
import com.views.calendar.cosmocalendar.settings.SettingsManager
import com.views.calendar.cosmocalendar.utils.CalendarUtils
import java.util.*

/**
 * Created by leonardo on 08/10/17.
 */
class FetchMonthsAsyncTask : AsyncTask<FetchParams, Void, List<Month>>() {

    class FetchParams(
            val future: Boolean,
            val month: Month,
            val settingsManager: SettingsManager,
            val monthAdapter: MonthAdapter,
            val defaultMonthCount: Int
    )

    private var future = false
    private var monthAdapter: MonthAdapter? = null
    private var defaultMonthCount = 0

    override fun doInBackground(vararg fetchParams: FetchParams): List<Month> {
        val params = fetchParams[0]
        val month = params.month
        future = params.future
        val settingsManager = params.settingsManager
        monthAdapter = params.monthAdapter
        defaultMonthCount = params.defaultMonthCount

        val calendar = Calendar.getInstance()
        calendar.time = month.firstDay.calendar.time
        val result: MutableList<Month> = ArrayList()
        for (i in 0 until SettingsManager.DEFAULT_MONTH_COUNT) {
            if (isCancelled) {
                break
            }
            calendar.add(Calendar.MONTH, if (future) 1 else -1)
            val newMonth = CalendarUtils.createMonth(calendar.time, settingsManager)
            if (future) {
                result.add(newMonth)
            } else {
                result.add(0, newMonth)
            }
        }
        return result
    }

    override fun onPostExecute(months: List<Month>) {
        monthAdapter?.let { adapter ->
            if (months.isNotEmpty()) {
                if (future) {
                    adapter.data.addAll(months)
                    adapter.notifyItemRangeInserted(adapter.data.size - 1, defaultMonthCount)
                } else {
                    adapter.data.addAll(0, months)
                    adapter.notifyItemRangeInserted(0, defaultMonthCount)
                }
            }
        }
    }
}
