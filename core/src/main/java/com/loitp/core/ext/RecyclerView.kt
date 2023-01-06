package com.loitp.core.ext

import androidx.recyclerview.widget.RecyclerView
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun RecyclerView.setPullLikeIOSVertical(
) {
    // guide: https://github.com/EverythingMe/overscroll-decor
    OverScrollDecoratorHelper.setUpOverScroll(
        /* recyclerView = */ this,
        /* orientation = */ OverScrollDecoratorHelper.ORIENTATION_VERTICAL
    )
}

fun RecyclerView.setPullLikeIOSHorizontal(
) {
    // guide: https://github.com/EverythingMe/overscroll-decor
    OverScrollDecoratorHelper.setUpOverScroll(
        /* recyclerView = */ this,
        /* orientation = */ OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL
    )
}

fun RecyclerView.setPullLikeIOSVertical(
    onUpOrLeft: ((Float) -> Unit)? = null,
    onUpOrLeftRefresh: ((Float) -> Unit)? = null,
    onDownOrRight: ((Float) -> Unit)? = null,
    onDownOrRightRefresh: ((Float) -> Unit)? = null
) {
    val mDecor = OverScrollDecoratorHelper.setUpOverScroll(
        /* recyclerView = */ this,
        /* orientation = */ OverScrollDecoratorHelper.ORIENTATION_VERTICAL
    )
    mDecor.setOverScrollUpdateListener { _, _, offset ->
        // val view = decor.view
        when {
            offset > 0 -> {
                // 'view' is currently being over-scrolled from the top.
                lastOffset = offset
                isUp = true
            }
            offset < 0 -> {
                // 'view' is currently being over-scrolled from the bottom.
                lastOffset = offset
                isUp = false
            }
            else -> {
                // No over-scroll is in-effect.
                // This is synonymous with having (state == STATE_IDLE).
                if (isUp) {
                    if (lastOffset > 1.8f) {
                        onUpOrLeftRefresh?.invoke(lastOffset)
                        context.startMusicFromAsset("ting.ogg")
                    } else {
                        onUpOrLeft?.invoke(lastOffset)
                    }
                } else {
                    if (lastOffset < -1.8f) {
                        onDownOrRightRefresh?.invoke(lastOffset)
                    } else {
                        onDownOrRight?.invoke(lastOffset)
                    }
                }
                lastOffset = 0f
                isUp = false
            }
        }
    }
}
