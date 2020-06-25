package com.core.utilities

import android.util.Log

import com.core.common.Constants

class LLog {
    companion object {
        @JvmStatic
        fun d(tag: String, msg: String) {
            if (Constants.IS_DEBUG) {
                Log.d(tag, msg)
            }
        }

        @JvmStatic
        fun e(tag: String, msg: String) {
            if (Constants.IS_DEBUG) {
                Log.e(tag, msg)
            }
        }

        @JvmStatic
        fun i(tag: String, msg: String) {
            if (Constants.IS_DEBUG) {
                Log.e(tag, msg)
            }
        }
    }

}
