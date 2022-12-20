package com.loitpcore.utils.util

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import com.loitp.core.utilities.LLog
import com.loitpcore.utils.util.Utils.Companion.getContext

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ProcessUtils {
    companion object {
        val foregroundProcessName: String?
            get() {
                val manager =
                    getContext()?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
                val pInfo = manager?.runningAppProcesses
                if (pInfo != null && pInfo.size != 0) {
                    for (aInfo in pInfo) {
                        if (aInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            return aInfo.processName
                        }
                    }
                }
                val packageManager = getContext()?.packageManager
                val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                val list = packageManager?.queryIntentActivities(
                    intent,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
                println(list)
                if (list?.isNotEmpty() == true) {
                    try {
                        val info = packageManager.getApplicationInfo(
                            getContext()?.packageName
                                ?: "",
                            0
                        )
                        val aom =
                            getContext()?.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager?
                        if (aom?.checkOpNoThrow(
                                AppOpsManager.OPSTR_GET_USAGE_STATS,
                                info.uid,
                                info.packageName
                            ) != AppOpsManager.MODE_ALLOWED
                        ) {
                            getContext()?.startActivity(intent)
                        }
                        if (aom?.checkOpNoThrow(
                                AppOpsManager.OPSTR_GET_USAGE_STATS,
                                info.uid,
                                info.packageName
                            ) != AppOpsManager.MODE_ALLOWED
                        ) {
                            LLog.d("getForegroundApp", "没有打开\"有权查看使用权限的应用\"选项")
                            return null
                        }
                        val usageStatsManager =
                            getContext()?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager?
                        val endTime = System.currentTimeMillis()
                        val beginTime = endTime - 86400000 * 7
                        val usageStatses = usageStatsManager?.queryUsageStats(
                            UsageStatsManager.INTERVAL_BEST,
                            beginTime,
                            endTime
                        )
                        if (usageStatses == null || usageStatses.isEmpty()) return null
                        var recentStats: UsageStats? = null
                        for (usageStats in usageStatses) {
                            if (recentStats == null || usageStats.lastTimeUsed > recentStats.lastTimeUsed) {
                                recentStats = usageStats
                            }
                        }
                        return recentStats?.packageName
                    } catch (e: PackageManager.NameNotFoundException) {
                        e.printStackTrace()
                    }
                }
                return null
            }
    }
}
