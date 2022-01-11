package com.core.utilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat

object LNotification {

    fun showNotification(
        title: String,
        body: String,
        iconRes: Int,
        notificationIntent: Intent
    ) {
        if (title.isEmpty() || body.isEmpty()) {
            return
        }

        val channelId = "CHANNEL_ID"
        val channelName = "CHANNEL_NAME"

        val requestID = System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getActivity(
            LAppResource.application,
            requestID,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationManager =
            LAppResource.application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(
            LAppResource.application,
            channelId
        )
            .setSmallIcon(iconRes)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(body)

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
}
