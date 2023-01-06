package com.loitp.core.base

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.google.gson.Gson
import com.loitp.annotation.LogTag
import com.loitp.core.ext.d
import com.loitp.core.ext.e
import com.loitp.core.ext.initOnNetworkChange
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LUIUtil
import com.loitp.core.utils.Utils
import com.loitp.game.findNumber.db.FindNumberDatabase


/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
// GIT
// combine 2 commit gan nhat lam 1, co thay doi tren github
/*git reset --soft HEAD~2
git push -f*/
open class BaseApplication : MultiDexApplication(), LifecycleObserver {

    private var logTag: String? = null
    private val mFTActivityLifecycleCallbacks = FTActivityLifecycleCallbacks()

    companion object {
        val gson: Gson = Gson()
        private var instance: BaseApplication? = null

        fun currentActivity(): Activity? {
            return instance?.mFTActivityLifecycleCallbacks?.currentActivity
        }
    }

    init {
        instance = this@BaseApplication
    }

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)
        registerActivityLifecycleCallbacks(mFTActivityLifecycleCallbacks)

        logTag = javaClass.getAnnotation(LogTag::class.java)?.value
        LAppResource.init(this)
        Utils.init(this)

        // big image view
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))

        // network
        this.initOnNetworkChange()

        // dark mode
        val isDarkTheme = LUIUtil.isDarkTheme()
        LUIUtil.setDarkTheme(isDarkTheme = isDarkTheme)

        //game find number database
        FindNumberDatabase.getInstance(this)
    }

    protected fun logD(msg: String) {
        logTag?.let {
            d(it, msg)
        }
    }

    protected fun logE(msg: String) {
        logTag?.let {
            e(it, msg)
        }
    }

//    fun isActivityVisible(): String {
//        return ProcessLifecycleOwner.get().lifecycle.currentState.name
//    }

    private val lifecycleEventObserver = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
//                logE("ON_CREATE")
            }
            Lifecycle.Event.ON_START -> {
//                logE("ON_START")
//                logE(">>>onAppInForeground")
                onAppInForeground()
            }
            Lifecycle.Event.ON_RESUME -> {
//                logE("ON_RESUME")
            }
            Lifecycle.Event.ON_PAUSE -> {
//                logE("ON_PAUSE")
            }
            Lifecycle.Event.ON_STOP -> {
//                logE("ON_STOP")
//                logE(">>>onAppInBackground")
                onAppInBackground()
            }
            Lifecycle.Event.ON_DESTROY -> {
//                logE("ON_DESTROY")
            }
            else -> {}
        }
    }

    open fun onAppInForeground() {}
    open fun onAppInBackground() {}
}
