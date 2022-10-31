package com.loitpcore.utils.constant

import androidx.annotation.IntDef

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class MemoryConstants {
    companion object {
        /**
         * Byte与Byte的倍数
         */
        const val BYTE = 1

        /**
         * KB与Byte的倍数
         */
        const val KB = 1024

        /**
         * MB与Byte的倍数
         */
        const val MB = 1048576

        /**
         * GB与Byte的倍数
         */
        const val GB = 1073741824

        @IntDef(BYTE, KB, MB, GB)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Unit
    }
}
