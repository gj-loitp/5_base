package com.function.notification.notifications

import androidx.core.app.NotificationCompat

class BIgTextNotification(
    title: String?,
    content: String?
) : BaseNotification() {

    init {
        this.title = title
        this.content = content
    }

    override fun setBuilder(builder: NotificationCompat.Builder): NotificationCompat.Builder {
        builder.setContentTitle(title).setContentText(content)
        setBuilderIcon(builder)
        setBigTextStyle(builder)
        return builder
    }

    fun setBuilderIcon(builder: NotificationCompat.Builder) {
        icon?.let {
            builder.setSmallIcon(it)
        }
    }

    fun setBigTextStyle(builder: NotificationCompat.Builder) {
        bigText?.let {
            builder.setStyle(NotificationCompat.BigTextStyle().bigText(it))
        }
    }
}
