package com.function.notification.notifications

import androidx.core.app.NotificationCompat

class StandardNotification(
        val title: String?,
        val content: String?
) : BaseNotification() {

    override fun setBuilder(builder: NotificationCompat.Builder): NotificationCompat.Builder {
        builder.setContentTitle(title)
                .setContentText(content)
        setBuilderIcon(builder)
        return builder
    }

    private fun setBuilderIcon(builder: NotificationCompat.Builder) {
        icon?.let {
            builder.setSmallIcon(it)
        }
    }
}
