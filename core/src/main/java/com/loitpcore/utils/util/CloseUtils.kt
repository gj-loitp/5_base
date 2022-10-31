package com.loitpcore.utils.util

import java.io.Closeable
import java.io.IOException

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class CloseUtils private constructor() {

    companion object {
        @JvmStatic
        fun closeIO(vararg closeables: Closeable?) {
            for (closeable in closeables) {
                try {
                    closeable?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        @Suppress("unused")
        fun closeIOQuietly(vararg closeables: Closeable?) {
            for (closeable in closeables) {
                try {
                    closeable?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
