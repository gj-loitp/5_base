package vn.loitp.app

import com.g1.onetargetsdk.core.Analytics
import com.g1.onetargetsdk.core.Configuration
import com.g1.onetargetsdk.core.IAM
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT
import com.loitp.core.helper.ttt.db.TTTDatabase
import com.loitp.data.ActivityData
import com.onesignal.OneSignal
import io.realm.Realm
import io.realm.RealmConfiguration
import vn.loitp.BuildConfig
import vn.loitp.a.db.room.db.FNBDatabase

// build release de check

//TODO service -> ko stop service dc
//TODO why see ad khi nhan vao button Understand dang sai behaviour
//TODO change link policy URL_POLICY_NOTION play console
//TODO The 'kotlin-android-extensions' Gradle plugin is no longer supported

// GIT
// combine 2 commit gan nhat lam 1, co thay doi tren github
/*git reset --soft HEAD~2
git push -f*/

//@Suppress("unused")

// activity transaction reveal khi finish screen co cai effect sai

//https://console.firebase.google.com/u/0/project/com-roygroup-base/overview
//https://app.onesignal.com/apps/f26cbe23-125a-4fe7-86d4-9353b4f7d2b0
@LogTag("LApplication")
class LApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        // config activity transition default
        ActivityData.instance.type = TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT

        // config realm
        val realmConfiguration = RealmConfiguration.Builder(this)
            .name(Realm.DEFAULT_REALM_NAME)
            .schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        // config font
//        fontForAll = Constants.FONT_PATH

        //room database
        FNBDatabase.getInstance(this)

        // ttt database
        TTTDatabase.getInstance(this)

        setupOneSignal()
//        setupTrackingG1()

        logE("currentActivity() ${currentActivity()}")
    }

    @Suppress("unused")
    private fun setupTrackingG1() {
        val configuration = Configuration(this)
        configuration.setEnvironmentDev()
        configuration.writeKey = "490bf1f1-2e88-4d6d-8ec4-2bb7de74f9a8"
        configuration.isShowLog = BuildConfig.DEBUG
        configuration.isEnableIAM = true
        configuration.oneTargetAppPushID = "d355f0df-6d85-4258-a871-82aaa4031b53"
        configuration.onShowIAM = { htmlContent, iamData ->
            IAM.showIAMActivity(this, htmlContent, iamData)
            //or IAM.showIAMDialog(this, htmlContent, iamData)
        }
        Analytics.setup(configuration = configuration, context = this)
    }

    override fun onAppInBackground() {
        super.onAppInBackground()
        logD("onAppInBackground")
    }

    override fun onAppInForeground() {
        super.onAppInForeground()
        logD("onAppInForeground")
    }

    private fun setupOneSignal() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(vn.loitp.common.Constants.ONE_SIGNAL_KEY)
        OneSignal.promptForPushNotifications()
    }

}
