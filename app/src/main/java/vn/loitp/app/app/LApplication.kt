package vn.loitp.app.app

import androidx.multidex.MultiDexApplication
import com.core.common.Constants
import com.core.utilities.LUIUtil
import com.data.ActivityData
import com.data.AdmobData
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.utils.util.Utils
import io.realm.Realm
import io.realm.RealmConfiguration
import loitp.basemaster.R

//TODO compass
//TODO bug custom view -> button -> loading button
//TODO crash FloatingViewActivity -> demo app -> floating view

//TODO core film plus
//TODO is debug
//TODO https://github.com/hackware1993/MagicIndicator

//GIT
//combine 2 commit gan nhat lam 1, co thay doi tren github
/*git reset --soft HEAD~2
git push -f*/

class LApplication : MultiDexApplication() {
    private val TAG = LApplication::class.java.simpleName

    companion object {
        val gson: Gson = Gson()
    }

    override fun onCreate() {
        super.onCreate()

        Constants.setIsDebug(true)
        Utils.init(this)
        //config admob id
        AdmobData.instance.idAdmobFull = getString(R.string.str_f)
        //config activity transition default
        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT

        //config realm
        val realmConfiguration = RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        //config font
        LUIUtil.fontForAll = Constants.FONT_PATH

        //fcm
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.FCM_TOPIC)

        //big imageview
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))
    }
}
