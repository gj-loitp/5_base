package com.loitpcore.utils.util

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import com.loitpcore.utils.util.Utils.Companion.getContext
import java.util.*

class ServiceUtils {
    companion object {

        val allRunningService: Set<*>?
            get() {
                val activityManager =
                    getContext()?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
                val info = activityManager?.getRunningServices(0x7FFFFFFF)
                val names: MutableSet<String> = HashSet()
                if (info == null || info.size == 0) return null
                for (aInfo in info) {
                    names.add(aInfo.service.className)
                }
                return names
            }

        fun startService(className: String) {
            try {
                startService(Class.forName(className))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun startService(cls: Class<*>?) {
            val intent = Intent(getContext(), cls)
            getContext()?.startService(intent)
        }

        fun stopService(className: String): Boolean? {
            return try {
                stopService(Class.forName(className))
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        fun stopService(cls: Class<*>?): Boolean? {
            val intent = Intent(getContext(), cls)
            return getContext()?.stopService(intent)
        }

        fun bindService(cls: Class<*>?, serviceConnection: ServiceConnection, flags: Int) {
            val intent = Intent(getContext(), cls)
            getContext()?.bindService(intent, serviceConnection, flags)
        }

        fun unbindService(conn: ServiceConnection) {
            getContext()?.unbindService(conn)
        }

        fun isServiceRunning(className: String): Boolean {
            val activityManager =
                getContext()?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
            val info = activityManager?.getRunningServices(0x7FFFFFFF)
            if (info == null || info.size == 0) return false
            for (aInfo in info) {
                if (className == aInfo.service.className) return true
            }
            return false
        }
    }
}
