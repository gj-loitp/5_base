package com.loitp.core.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.loitp.core.utilities.LLog

/**
 * Created by Loitp on 29,September,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FTActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    var currentActivity: Activity? = null

    private fun logI(msg: String) {
        LLog.i(FTActivityLifecycleCallbacks::class.java.simpleName, msg)
    }

    override fun onActivityPaused(activity: Activity) {
        currentActivity = activity
        logI("onActivityPaused ${activity::class.java.simpleName}")
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
        logI("onActivityResumed ${activity::class.java.simpleName}")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentActivity = activity
        logI("onActivityCreated ${activity::class.java.simpleName}")
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
        logI("onActivityStarted ${activity::class.java.simpleName}")
    }

    override fun onActivityDestroyed(activity: Activity) {
        logI("onActivityDestroyed ${activity::class.java.simpleName}")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        logI("onActivitySaveInstanceState ${activity::class.java.simpleName}")
    }

    override fun onActivityStopped(activity: Activity) {
        logI("onActivityStopped ${activity::class.java.simpleName}")
    }
}
