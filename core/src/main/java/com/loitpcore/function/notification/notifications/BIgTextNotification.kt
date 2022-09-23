package com.loitpcore.function.notification.notifications

import androidx.core.app.NotificationCompat

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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

    private fun setBuilderIcon(builder: NotificationCompat.Builder) {
        icon?.let {
            builder.setSmallIcon(it)
        }
    }

    private fun setBigTextStyle(builder: NotificationCompat.Builder) {
        bigText?.let {
            builder.setStyle(NotificationCompat.BigTextStyle().bigText(it))
        }
    }
}
