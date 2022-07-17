package com.loitpcore.core.base

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

// GIT
// combine 2 commit gan nhat lam 1, co thay doi tren github
/*git reset --soft HEAD~2
git push -f*/

open class BaseApplication : MultiDexApplication() {

    private var logTag: String? = null

    companion object {
        val gson: Gson = Gson()
    }

    override fun onCreate() {
        super.onCreate()

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
}
