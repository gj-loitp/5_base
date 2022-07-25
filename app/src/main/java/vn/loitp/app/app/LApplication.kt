package vn.loitp.app.app

import com.g1.onetargetsdk.Analytics
import com.g1.onetargetsdk.AnalyticsConfiguration
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.common.Constants
import com.loitpcore.core.helper.ttt.db.TTTDatabase
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.data.ActivityData
import com.loitpcore.data.AdmobData
import io.realm.Realm
import io.realm.RealmConfiguration
import vn.loitp.app.R
import vn.loitp.app.activity.database.room.db.FNBDatabase
import vn.loitp.app.activity.service.endlessService.log

// build release de check
// TODO service -> ko stop service dc

// GIT
// combine 2 commit gan nhat lam 1, co thay doi tren github
/*git reset --soft HEAD~2
git push -f*/

// activity transaction reveal khi finish screen co cai effect sai

@LogTag("LApplication")
class LApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        // config admob id
        AdmobData.instance.idAdmobFull = getString(R.string.str_f)

        // config activity transition default
        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT

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

        setupTracking()
    }

    private fun setupTracking() {
        val configuration = AnalyticsConfiguration()
        configuration.setEnvironmentDev()
//        configuration.setEnvironmentProd()
        configuration.writeKey = "ab44219f-dc9e-4080-943c-a127bd071da3"
        configuration.email = "example@gmail.com"
        configuration.phone = "039889981"
        configuration.deviceId = Analytics.getDeviceId(this)

        val result = Analytics.setup(configuration)
        log("setup result $result")
    }
}
