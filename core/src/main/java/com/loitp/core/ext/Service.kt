package com.loitp.core.ext

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.Looper
import com.loitp.core.utils.Utils

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun setDelay(
    mls: Int,
    runnable: Runnable
) {
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({ runnable.run() }, mls.toLong())
}

val isUIThread: Boolean
    get() = Looper.myLooper() == Looper.getMainLooper()

@Suppress("unused")
val allRunningService: Set<*>?
    get() {
        val activityManager =
            Utils.getContext()?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        val info = activityManager?.getRunningServices(0x7FFFFFFF)
        val names: MutableSet<String> = HashSet()
        if (info == null || info.size == 0) return null
        for (aInfo in info) {
            names.add(aInfo.service.className)
        }
        return names
    }

@Suppress("unused")
fun Context.startService(className: String) {
    try {
        startService(Class.forName(className))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Suppress("unused")
fun Context.startService(cls: Class<*>?) {
    val intent = Intent(this, cls)
    this.startService(intent)
}

fun Context.stopService(className: String): Boolean? {
    return try {
        stopService(Class.forName(className))
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

@Suppress("unused")
fun Context.stopService(cls: Class<*>?): Boolean? {
    val intent = Intent(this, cls)
    return this.stopService(intent)
}

@Suppress("unused")
fun Context.bindService(
    cls: Class<*>?,
    serviceConnection: ServiceConnection,
    flags: Int
) {
    val intent = Intent(this, cls)
    this.bindService(intent, serviceConnection, flags)
}

@Suppress("unused")
fun Context.unbindServices(conn: ServiceConnection) {
    this.unbindService(conn)
}

@Suppress("unused")
fun Context.isServiceRunning(className: String): Boolean {
    val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
    val info = activityManager?.getRunningServices(0x7FFFFFFF)
    if (info == null || info.size == 0) return false
    for (aInfo in info) {
        if (className == aInfo.service.className) return true
    }
    return false
}
