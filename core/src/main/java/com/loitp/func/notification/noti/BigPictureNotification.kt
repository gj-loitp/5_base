package com.loitp.func.notification.noti

import androidx.core.app.NotificationCompat

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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

    private fun setBuilderIcon(builder: NotificationCompat.Builder) {
        icon?.let {
            builder.setSmallIcon(it)
        }
    }

    private fun setBigPictureStyle(builder: NotificationCompat.Builder) {
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
