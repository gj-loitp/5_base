package vn.loitp.core.utilities

import android.util.Log

import vn.loitp.core.common.Constants

object LLog {
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