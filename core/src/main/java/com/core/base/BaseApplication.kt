package com.core.base

import androidx.multidex.MultiDexApplication
import com.annotation.LogTag
import com.core.helper.mup.girl.db.GirlDatabase
import com.core.utilities.LAppResource
import com.core.utilities.LConnectivityUtil
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.game.findnumber.db.FindNumberDatabase
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.google.gson.Gson
import com.utils.util.Utils

//GIT
//combine 2 commit gan nhat lam 1, co thay doi tren github
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

        //big image view
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))

        //network
        LConnectivityUtil.initOnNetworkChange()

        //room database
        GirlDatabase.getInstance(this)
        FindNumberDatabase.getInstance(this)

        val isDarkTheme = LUIUtil.isDarkTheme()
        if (isDarkTheme) {
            LUIUtil.setDarkTheme(isDarkTheme = false)
        } else {
            LUIUtil.setDarkTheme(isDarkTheme = true)
        }
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
