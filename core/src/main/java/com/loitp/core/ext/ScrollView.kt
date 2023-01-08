package com.loitp.core.ext

import android.widget.GridView
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun ScrollView.setPullLikeIOSVertical(
) {
    // guide: https://github.com/EverythingMe/overscroll-decor
    OverScrollDecoratorHelper.setUpOverScroll(this)
}

fun ListView.setPullLikeIOSVertical(
) {
    // guide: https://github.com/EverythingMe/overscroll-decor
    OverScrollDecoratorHelper.setUpOverScroll(this)
}

fun GridView.setPullLikeIOSVertical(
) {
    // guide: https://github.com/EverythingMe/overscroll-decor
    OverScrollDecoratorHelper.setUpOverScroll(this)
}

fun HorizontalScrollView.setPullLikeIOSVertical(
) {
    // guide: https://github.com/EverythingMe/overscroll-decor
    OverScrollDecoratorHelper.setUpOverScroll(this)
}

@Suppress("unused")
fun ScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}

fun NestedScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}
