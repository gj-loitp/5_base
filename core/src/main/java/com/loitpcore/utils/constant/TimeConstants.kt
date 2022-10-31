package com.loitpcore.utils.constant

import androidx.annotation.IntDef

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class TimeConstants {
    companion object {
        /**
         * 毫秒与毫秒的倍数
         */
        const val MSEC = 1

        /**
         * 秒与毫秒的倍数
         */
        const val SEC = 1000

        /**
         * 分与毫秒的倍数
         */
        const val MIN = 60000

        /**
         * 时与毫秒的倍数
         */
        const val HOUR = 3600000

        /**
         * 天与毫秒的倍数
         */
        const val DAY = 86400000

        @IntDef(MSEC, SEC, MIN, HOUR, DAY)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Unit
    }
}
