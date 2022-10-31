package com.loitpcore.function.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.loitpcore.function.notification.config.NottiConfig
import com.loitpcore.function.notification.notifications.CustomNotification
import com.loitpcore.utils.util.AppUtils

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class Notti(
    private val context: Context,
    private val nottiConfig: NottiConfig
) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    @Suppress("unused")
    private val ids: MutableMap<String, Int> = HashMap()
    private var currentID = 0

    fun show(customNotification: CustomNotification) {
        val channelName = AppUtils.appName
        val channelId = "channel-$channelName"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
        builder.priority = NotificationCompat.PRIORITY_MAX
        setBuilderWithConfig(builder = builder)
        customNotification.setBuilder(builder)
        setActionsForNotification(builder = builder, customNotification = customNotification)
        setContentIntent(customNotification = customNotification, builder = builder)
        setDiode(customNotification = customNotification, builder = builder)
        setVibrations(customNotification = customNotification, builder = builder)
        notificationManager.notify(notificationID, builder.build())
    }

    private fun setVibrations(
        customNotification: CustomNotification,
        builder: NotificationCompat.Builder
    ) {
        val vibrationSettings = nottiConfig.vibrationSettings
        val customVibrationSettings = customNotification.vibrationSettings
        if (vibrationSettings != null && vibrationSettings.isVibrate) {
            builder.setVibrate(vibrationSettings.pattern)
        } else if (customVibrationSettings != null && customVibrationSettings.isVibrate) {
            builder.setVibrate(customVibrationSettings.pattern)
        }
    }

    private fun setDiode(
        customNotification: CustomNotification,
        builder: NotificationCompat.Builder
    ) {
        val lightSettings = nottiConfig.lightSettings
        val customLightSettings = customNotification.lightSettings
        if (lightSettings != null) {
            builder.setLights(lightSettings.argb, lightSettings.onMs, lightSettings.offMs)
        } else if (customLightSettings != null) {
            builder.setLights(
                customLightSettings.argb,
                customLightSettings.onMs,
                customLightSettings.offMs
            )
        }
    }

    private fun setContentIntent(
        customNotification: CustomNotification,
        builder: NotificationCompat.Builder
    ) {
        if (customNotification.contentAction != null) {
            builder.setContentIntent(customNotification.contentAction.getPendingIntent())
        }
    }

    private fun provideIcon(icon: Int): Int {
        return icon
    }

    private fun setActionsForNotification(
        builder: NotificationCompat.Builder,
        customNotification: CustomNotification
    ) {
        if (customNotification.actions != null) {
            for (notificationAction in customNotification.actions) {
                builder.addAction(
                    provideIcon(icon = notificationAction.image),
                    notificationAction.text,
                    notificationAction.getPendingIntent()
                )
            }
        }
    }

    private val notificationID: Int
        get() = if (!nottiConfig.isSameID) {
            currentID++
        } else {
            0
        }

    private fun setBuilderWithConfig(builder: NotificationCompat.Builder) {
        builder.setSmallIcon(nottiConfig.defaultActionImage)
    }
}
