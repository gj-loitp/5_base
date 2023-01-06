package com.loitp.core.ext

import androidx.viewpager.widget.ViewPager
import com.loitp.core.utilities.LSoundUtil
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun ViewPager.setPullLikeIOSHorizontal(
    onUpOrLeft: ((Float) -> Unit)? = null,
    onUpOrLeftRefresh: ((Float) -> Unit)? = null,
    onDownOrRight: ((Float) -> Unit)? = null,
    onDownOrRightRefresh: ((Float) -> Unit)? = null
) {
    // guide: https://github.com/EverythingMe/overscroll-decor
    val mDecor = OverScrollDecoratorHelper.setUpOverScroll(this)
    mDecor.setOverScrollUpdateListener { _, _, offset ->
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
                        LSoundUtil.startMusicFromAsset("ting.ogg")
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

fun ViewPager.setPullLikeIOSHorizontal(
) {
    // guide: https://github.com/EverythingMe/overscroll-decor
    OverScrollDecoratorHelper.setUpOverScroll(this)
}
