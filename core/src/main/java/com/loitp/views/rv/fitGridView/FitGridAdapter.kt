package com.loitp.views.rv.fitGridView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class FitGridAdapter : BaseAdapter {

    private var columnWidth = 0
    private var columnHeight = 0
    private var row = 0
    private var column = 0
    private var layoutRes = 0
    private var size = 0
    private var context: Context

    constructor(
        context: Context,
        itemId: Int
    ) {
        this.context = context
        this.layoutRes = itemId
    }

    @Suppress("unused")
    constructor(
        context: Context,
        itemId: Int,
        size: Int
    ) {
        this.context = context
        this.layoutRes = itemId
        this.size = size
    }

    @Suppress("unused")
    constructor(
        context: Context,
        itemId: Int,
        row: Int,
        column: Int
    ) {
        this.context = context
        this.layoutRes = itemId
        this.row = row
        this.column = column
        size = row * column
    }

    override fun getView(
        position: Int,
        itemView: View?,
        parent: ViewGroup
    ): View? {
        var mItemView = itemView
        if (mItemView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            mItemView = inflater.inflate(layoutRes, parent, false)
            val params = AbsListView.LayoutParams(columnWidth, columnHeight)
            mItemView.layoutParams = params
            onBindView(position = position, view = mItemView)
        }
        return mItemView
    }

    abstract fun onBindView(
        position: Int,
        view: View
    )

    override fun getCount(): Int {
        return if (size == 0) {
            column * row
        } else {
            size
        }
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    fun setColumnHeight(columnHeight: Int) {
        this.columnHeight = columnHeight
    }

    fun setColumnWidth(columnWidth: Int) {
        this.columnWidth = columnWidth
    }

    fun setNumColumns(column: Int) {
        this.column = column
    }

    fun setNumRows(row: Int) {
        this.row = row
    }
}
