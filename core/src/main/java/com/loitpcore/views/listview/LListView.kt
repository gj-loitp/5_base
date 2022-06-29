package com.loitpcore.views.listview

import android.content.Context
import android.util.AttributeSet
import android.widget.AbsListView
import android.widget.ListView

open class LListView : ListView {

    private var onScrollListener: OnScrollListener? = null
    private var onDetectScrollListener: OnDetectScrollListener? = null

    constructor(context: Context) : super(context) {
        onCreate(context, null, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        onCreate(context, attrs, null)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        onCreate(context, attrs, defStyle)
    }

    private fun onCreate(context: Context, attrs: AttributeSet?, defStyle: Int?) {
        setListeners()
    }

    private fun setListeners() {
        super.setOnScrollListener(object : OnScrollListener {

            private var oldTop = 0
            private var oldFirstVisibleItem = 0
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                onScrollListener?.onScrollStateChanged(view, scrollState)
            }

            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                onScrollListener?.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount)
                if (onDetectScrollListener != null) {
                    onDetectedListScroll(view, firstVisibleItem)
                }
            }

            private fun onDetectedListScroll(absListView: AbsListView, firstVisibleItem: Int) {
                val view = absListView.getChildAt(0)
                val top = view?.top ?: 0
                if (firstVisibleItem == oldFirstVisibleItem) {
                    if (top > oldTop) {
                        onDetectScrollListener?.onUpScrolling()
                    } else if (top < oldTop) {
                        onDetectScrollListener?.onDownScrolling()
                    }
                } else {
                    if (firstVisibleItem < oldFirstVisibleItem) {
                        onDetectScrollListener?.onUpScrolling()
                    } else {
                        onDetectScrollListener?.onDownScrolling()
                    }
                }
                oldTop = top
                oldFirstVisibleItem = firstVisibleItem
            }
        })
    }

    override fun setOnScrollListener(onScrollListener: OnScrollListener) {
        this.onScrollListener = onScrollListener
    }

    fun setOnDetectScrollListener(onDetectScrollListener: OnDetectScrollListener?) {
        this.onDetectScrollListener = onDetectScrollListener
    }
}
