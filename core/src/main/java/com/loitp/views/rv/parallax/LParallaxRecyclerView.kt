package com.loitp.views.rv.parallax

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LParallaxRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    private fun dp2px(
        context: Context,
        dipValue: Float
    ): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    init {
        if (!isInEditMode) {
            layoutManager = LinearLayoutManager(context)

            addItemDecoration(object : ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)

                    val currentPosition = parent.getChildLayoutPosition(view)
                    val lastPosition = state.itemCount - 1
                    if (currentPosition != lastPosition) {
                        outRect.bottom = -dp2px(context, 10f)
                    }
                }
            })
            addOnScrollListener(object : OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    if (layoutManager != null) {
                        val firstPosition = layoutManager.findFirstVisibleItemPosition()
                        val lastPosition = layoutManager.findLastVisibleItemPosition()
                        val visibleCount = lastPosition - firstPosition
                        var elevation = 1
                        for (i in firstPosition - 1..firstPosition + visibleCount + 1) {
                            val view = layoutManager.findViewByPosition(i)
                            if (view != null) {
                                if (view is CardView) {
                                    view.cardElevation =
                                        dp2px(context, elevation.toFloat()).toFloat()
                                    elevation += 5
                                }
                                val translationY = view.translationY
                                if (i > firstPosition && translationY != 0f) {
                                    view.translationY = 0f
                                }
                            }
                        }
                        val firstView = layoutManager.findViewByPosition(firstPosition)
                        if (firstView != null) {
                            val firstViewTop = firstView.top.toFloat()
                            firstView.translationY = -firstViewTop / 2.0f
                        }
                    }
                }
            })
        }
    }
}
