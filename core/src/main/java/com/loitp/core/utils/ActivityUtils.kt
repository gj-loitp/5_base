package com.loitp.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.ArrayMap

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ActivityUtils private constructor() {
    companion object {

        /**
         * 判断是否存在Activity
         *
         * @param packageName 包名
         * @param className   activity全路径类名
         * @return `true`: 是<br></br>`false`: 否
         */
        fun isActivityExists(
            packageName: String?,
            className: String?
        ): Boolean {
            val context = Utils.getContext()
            if (context == null || packageName.isNullOrEmpty() || className.isNullOrEmpty()) {
                return false
            }
            val intent = Intent()
            intent.setClassName(packageName, className)
            return !(
                    context.packageManager.resolveActivity(intent, 0) == null ||
                            intent.resolveActivity(context.packageManager) == null ||
                            context.packageManager.queryIntentActivities(intent, 0).size == 0
                    )
        }

        /**
         * 获取launcher activity
         *
         * @param packageName 包名
         * @return launcher activity
         */
        fun getLauncherActivity(
            packageName: String?
        ): String {
            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pm = Utils.getContext()?.packageManager
            val info = pm?.queryIntentActivities(intent, 0)
            info?.forEach { aInfo ->
                if (aInfo.activityInfo.packageName == packageName) {
                    return aInfo.activityInfo.name
                }
            }
            return "no $packageName"
        }

        /**
         * 获取栈顶Activity
         *
         * @return 栈顶Activity
         */
        val topActivity: Activity?
            @SuppressLint("PrivateApi")
            get() {
                try {
                    val activityThreadClass = Class.forName("android.app.ActivityThread")
                    val activityThread =
                        activityThreadClass.getMethod("currentActivityThread").invoke(null)
                    val activitiesField = activityThreadClass.getDeclaredField("mActivities")
                    activitiesField.isAccessible = true
                    val activities: Map<*, *> = activitiesField[activityThread] as ArrayMap<*, *>

                    for (activityRecord in activities.values) {
                        activityRecord?.let { ar ->
                            val activityRecordClass: Class<*> = ar.javaClass
                            val pausedField = activityRecordClass.getDeclaredField("paused")
                            pausedField.isAccessible = true
                            if (!pausedField.getBoolean(activityRecord)) {
                                val activityField = activityRecordClass.getDeclaredField("activity")
                                activityField.isAccessible = true
                                return activityField[activityRecord] as Activity
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
    }
}
