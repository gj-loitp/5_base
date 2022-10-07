package vn.loitp.app.app

import com.g1.onetargetsdk.core.Analytics
import com.g1.onetargetsdk.core.Configuration
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.common.Constants
import com.loitpcore.core.helper.ttt.db.TTTDatabase
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.data.ActivityData
import io.realm.Realm
import io.realm.RealmConfiguration
import vn.loitp.app.activity.database.room.db.FNBDatabase

// build release de check
// TODO service -> ko stop service dc
//TODO fix ko chay dc o android 13

// GIT
// combine 2 commit gan nhat lam 1, co thay doi tren github
/*git reset --soft HEAD~2
git push -f*/

// activity transaction reveal khi finish screen co cai effect sai

@LogTag("LApplication")
class LApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        // config activity transition default
        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT

        // config realm
        val realmConfiguration = RealmConfiguration.Builder(this)
            .name(Realm.DEFAULT_REALM_NAME)
            .schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        // config font
        LUIUtil.fontForAll = Constants.FONT_PATH

        //room database
        FNBDatabase.getInstance(this)

        // ttt database
        TTTDatabase.getInstance(this)

        setupTrackingG1()

        logE("currentActivity() ${currentActivity()}")
    }

    private fun setupTrackingG1() {
        val configuration = Configuration(this)
        configuration.setEnvironmentDev()
//        configuration.setEnvironmentProd()
        configuration.writeKey = "ab44219f-dc9e-4080-943c-a127bd071da3"
        val result = Analytics.setup(configuration)
        logE("setup result $result")
    }

    override fun onAppInBackground() {
        super.onAppInBackground()
        logE("onAppInBackground")
    }

    override fun onAppInForeground() {
        super.onAppInForeground()
        logE("onAppInForeground")
    }

}
