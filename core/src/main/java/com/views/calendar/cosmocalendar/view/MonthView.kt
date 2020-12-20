package com.views.calendar.cosmocalendar.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.views.calendar.cosmocalendar.adapter.DaysAdapter
import com.views.calendar.cosmocalendar.model.Month
import com.views.calendar.cosmocalendar.utils.Constants

class MonthView : FrameLayout {
    private var rvDays: RecyclerView? = null

    constructor(
            context: Context
    ) : super(context) {
        init()
    }

    constructor(
            context: Context,
            attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
            context: Context,
            attrs: AttributeSet?,
            @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context,
            attrs: AttributeSet?,
            @AttrRes defStyleAttr: Int,
            @StyleRes defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        rvDays = RecyclerView(context)
        rvDays?.let {
            it.setHasFixedSize(true)
            it.isNestedScrollingEnabled = false
            it.layoutParams = generateLayoutParams()
            val layoutManager = GridLayoutManager(context, Constants.DAYS_IN_WEEK)
            it.layoutManager = layoutManager
            addView(it)
        }
    }

    var adapter: DaysAdapter?
        get() = rvDays?.adapter as DaysAdapter?
        set(adapter) {
            rvDays?.adapter = adapter
        }

    fun initAdapter(month: Month?) {
        adapter?.setMonth(month)
    }

    private fun generateLayoutParams(): LayoutParams {
        return LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
