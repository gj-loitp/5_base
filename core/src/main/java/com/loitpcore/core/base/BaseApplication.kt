package com.loitpcore.core.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.google.gson.Gson
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.utilities.LAppResource
import com.loitpcore.core.utilities.LConnectivityUtil
import com.loitpcore.core.utilities.LLog
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.game.findNumber.db.FindNumberDatabase
import com.loitpcore.utils.util.Utils


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

    companion object {
        val gson: Gson = Gson()
    }

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleEventObserver)

        logTag = javaClass.getAnnotation(LogTag::class.java)?.value
        LAppResource.init(this)
        Utils.init(this)

        // big image view
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))

        // network
        LConnectivityUtil.initOnNetworkChange()

        // dark mode
        val isDarkTheme = LUIUtil.isDarkTheme()
        LUIUtil.setDarkTheme(isDarkTheme = isDarkTheme)

        //game find number database
        FindNumberDatabase.getInstance(this)
    }

    protected fun logD(msg: String) {
        logTag?.let {
            LLog.d(it, msg)
        }
    }

    protected fun logE(msg: String) {
        logTag?.let {
            LLog.e(it, msg)
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
