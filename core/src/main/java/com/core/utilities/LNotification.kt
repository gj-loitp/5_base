package com.core.utilities

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.utils.util.AppUtils

object LNotification {
    @Suppress("DEPRECATION")
    fun showNotification(context: Context, title: String, body: String, iconRes: Int, intent: Intent) {
        if (title.isEmpty() || body.isEmpty()) {
            return
        }
        val notifManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFY_ID = System.currentTimeMillis().toInt()
        val name = AppUtils.getAppPackageName()
        val id = "id$name"
        val description = "description$name"

        intent.action = System.currentTimeMillis().toString()
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder: NotificationCompat.Builder

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            var mChannel: NotificationChannel? = notifManager.getNotificationChannel(id)
            if (mChannel == null) {
                mChannel = NotificationChannel(id, name, importance)
                mChannel.description = description
                mChannel.enableVibration(true)
                mChannel.lightColor = Color.YELLOW
                mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                notifManager.createNotificationChannel(mChannel)
            }
            builder = NotificationCompat.Builder(context, id)
            //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            builder.setContentTitle(title)  // required
                    .setContentText(body)
                    .setSmallIcon(iconRes) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(title)
                    .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
        } else {
            builder = NotificationCompat.Builder(context)
            //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            builder.setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(iconRes)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(title)
                    .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)).priority = Notification.PRIORITY_HIGH
        }

        val notification = builder.build()
        notifManager.notify(NOTIFY_ID, notification)
    }
}
