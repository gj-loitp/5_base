package com.loitp.func.notification.notifications

import androidx.core.app.NotificationCompat

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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
