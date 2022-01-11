package com.utils.util

import java.io.Closeable
import java.io.IOException

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
