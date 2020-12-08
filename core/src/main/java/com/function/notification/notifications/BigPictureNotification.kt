package com.function.notification.notifications

import androidx.core.app.NotificationCompat

class BigPictureNotification(
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
        setBigPictureStyle(builder)
        return builder
    }

    fun setBuilderIcon(builder: NotificationCompat.Builder) {
        icon?.let {
            builder.setSmallIcon(it)
        }
    }

    fun setBigPictureStyle(builder: NotificationCompat.Builder) {
        val style = NotificationCompat.BigPictureStyle()
        bigPicture?.let {
            style.bigPicture(it)
        }
        largeImage?.let {
            builder.setLargeIcon(it)
        }
        builder.setStyle(style)
    }
}
