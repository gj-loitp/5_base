package com.loitp.views.layout.swipeBack.tools

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class WxSwipeBackActivityManager private constructor() : ActivityLifecycleCallbacksAdapter() {

    companion object {
        val instance = WxSwipeBackActivityManager()
    }

    private val mActivityStack = Stack<Activity>()

    fun init(mApplication: Application) {
        mApplication.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?
    ) {
        mActivityStack.add(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        mActivityStack.remove(activity)
    }

    val penultimateActivity: Activity?
        get() = if (mActivityStack.size >= 2) {
            mActivityStack[mActivityStack.size - 2]
        } else {
            null
        }
}
