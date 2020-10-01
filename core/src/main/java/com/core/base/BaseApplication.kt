package com.core.base

import androidx.multidex.MultiDexApplication
import com.core.helper.girl.db.GirlDatabase
import com.core.utilities.LAppResource
import com.core.utilities.LConnectivityUtil
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.google.gson.Gson
import com.utils.util.Utils

//need add nice repo
//https://github.com/hackware1993/MagicIndicator

//GIT
//combine 2 commit gan nhat lam 1, co thay doi tren github
/*git reset --soft HEAD~2
git push -f*/

open class BaseApplication : MultiDexApplication() {

    companion object {
        val gson: Gson = Gson()
    }

    override fun onCreate() {
        super.onCreate()

        LAppResource.init(this)
        Utils.init(this)

        //big image view
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))

        //network
        LConnectivityUtil.initOnNetworkChange(applicationContext)

        //room database
        GirlDatabase.getInstance(this)
    }
}
