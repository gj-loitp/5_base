package com.loitp.core.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import com.loitp.core.utils.Utils.Companion.getContext

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ServiceUtils {
    companion object {

        @Suppress("unused")
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

        @Suppress("unused")
        fun startService(className: String) {
            try {
                startService(Class.forName(className))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @Suppress("unused")
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

        @Suppress("unused")
        fun stopService(cls: Class<*>?): Boolean? {
            val intent = Intent(getContext(), cls)
            return getContext()?.stopService(intent)
        }

        @Suppress("unused")
        fun bindService(
            cls: Class<*>?,
            serviceConnection: ServiceConnection,
            flags: Int
        ) {
            val intent = Intent(getContext(), cls)
            getContext()?.bindService(intent, serviceConnection, flags)
        }

        @Suppress("unused")
        fun unbindService(conn: ServiceConnection) {
            getContext()?.unbindService(conn)
        }

        @Suppress("unused")
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
