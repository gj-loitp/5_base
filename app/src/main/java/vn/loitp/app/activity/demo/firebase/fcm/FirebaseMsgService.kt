package vn.loitp.app.activity.demo.firebase.fcm

import android.content.Intent
import android.graphics.Color
import com.core.common.Constants.Companion.IS_DEBUG
import com.core.utilities.LLog
import com.function.notification.Notti
import com.function.notification.NottiFactory
import com.function.notification.actions.ContentAction
import com.function.notification.config.LightSettings
import com.function.notification.config.NottiConfig
import com.function.notification.config.VibrationSettings
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.utils.util.AppUtils
import vn.loitp.app.R
import vn.loitp.app.activity.SplashActivity

class FirebaseMsgService : FirebaseMessagingService() {
    private val logTag = javaClass.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        try {
            if (remoteMessage.notification == null) {
                return
            }

            //THIS CODE BELOWS SEND A NOTIFICATION
            if (IS_DEBUG) {
                val appName = AppUtils.getAppName()
                val title = "$appName miss you!"
                val messageBody = remoteMessage.notification?.body
                val notti = Notti(
                        this,
                        NottiConfig(
                                R.mipmap.ic_launcher,
                                VibrationSettings(*VibrationSettings.STD_VIBRATION),
                                LightSettings(Color.RED)
                        )
                )
                notti.show(
                        NottiFactory
                                .get(NottiFactory.TYPE.STANDARD, title, messageBody)
                                .setContentAction(ContentAction(
                                        Intent(this, SplashActivity::class.java), this)
                                )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        LLog.d(logTag, "onNewToken $p0")
    }

}