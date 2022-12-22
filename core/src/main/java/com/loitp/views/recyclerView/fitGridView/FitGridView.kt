package com.loitp.views.recyclerView.fitGridView

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FitGridView : GridView {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private var column = 0

    var numRows = 0
    private fun init(attrs: AttributeSet?) {
        getAttributes(attrs)
        stretchMode = STRETCH_COLUMN_WIDTH
        numColumns = column
    }

    private fun getAttributes(attrs: AttributeSet?) {
        if (null == attrs) {
            return
        }
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FitGridView)
        column = typedArray.getInt(R.styleable.FitGridView_column, 0)
        numRows = typedArray.getInt(R.styleable.FitGridView_row, 0)
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        remeasure(width = w, height = h)
        updateAdapter()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        remeasure(width = width, height = height)
        setMeasuredDimension(width, height)
    }

    /**
     * use it to update view if you have changed width/height, grid size or adapter.
     */
    private fun updateAdapter() {
        if (null == fitGridAdapter) {
            return
        }
        fitGridAdapter?.let {
            it.setNumRows(numRows)
            it.setNumColumns(column)
            it.setColumnHeight(itemHeight)
            it.setColumnWidth(itemWidth)
        }
        adapter = fitGridAdapter
    }

    fun update() {
        remeasure(measuredWidth, measuredHeight)
        updateAdapter()
    }

    /**
     * @param displayWidth  sets max available width for grid view
     * @param displayHeight sets max available height for grid view
     */
    @Suppress("unused")
    fun setDimension(
        displayWidth: Float,
        displayHeight: Float
    ) {
        itemWidth = displayWidth.toInt() / column
        itemHeight = displayHeight.toInt() / numRows
        updateAdapter()
    }

    private var itemWidth = 0
    private var itemHeight = 0
    private fun remeasure(width: Int, height: Int) {
        itemWidth = width / if (column == 0) 1 else column
        itemHeight = height / if (numRows == 0) 1 else numRows
    }

    /**
     * @return Number of columns associated with the view
     */
    override fun getNumColumns(): Int {
        return column
    }

    /**
     * @param column sets the desired number of columns in the grid
     */
    override fun setNumColumns(
        column: Int
    ) {
        this.column = column
        super.setNumColumns(column)
    }

    private var fitGridAdapter: FitGridAdapter? = null

    /**
     * @param fitGridAdapter sets your adapter later in updateAdapter method.
     */
    fun setFitGridAdapter(
        fitGridAdapter: FitGridAdapter?
    ) {
        this.fitGridAdapter = fitGridAdapter
    }
}
