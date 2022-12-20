package com.loitp.core.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.loitpcore.core.utilities.LLog

/**
 * Created by Loitp on 29,September,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FTActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    var currentActivity: Activity? = null

    private fun logE(msg: String) {
        LLog.e(FTActivityLifecycleCallbacks::class.java.simpleName, msg)
    }

    override fun onActivityPaused(activity: Activity) {
        currentActivity = activity
        logE("onActivityPaused ${activity::class.java.simpleName}")
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
        logE("onActivityResumed ${activity::class.java.simpleName}")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentActivity = activity
        logE("onActivityCreated ${activity::class.java.simpleName}")
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
        logE("onActivityStarted ${activity::class.java.simpleName}")
    }

    override fun onActivityDestroyed(activity: Activity) {
        logE("onActivityDestroyed ${activity::class.java.simpleName}")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        logE("onActivitySaveInstanceState ${activity::class.java.simpleName}")
    }

    override fun onActivityStopped(activity: Activity) {
        logE("onActivityStopped ${activity::class.java.simpleName}")
    }
}
