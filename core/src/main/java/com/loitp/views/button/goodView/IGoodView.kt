package com.loitp.views.button.goodView

import android.graphics.Color

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
interface IGoodView {

    companion object {
        const val DISTANCE = 60 // 默认移动距离
        const val FROM_Y_DELTA = 0 // Y轴移动起始偏移量
        const val TO_Y_DELTA = DISTANCE // Y轴移动最终偏移量
        const val FROM_ALPHA = 1.0f // 起始时透明度
        const val TO_ALPHA = 0.0f // 结束时透明度
        const val DURATION = 800 // 动画时长
        const val TEXT = "" // 默认文本
        const val TEXT_SIZE = 16 // 默认文本字体大小
        const val TEXT_COLOR = Color.BLACK // 默认文本字体颜色
    }
}
