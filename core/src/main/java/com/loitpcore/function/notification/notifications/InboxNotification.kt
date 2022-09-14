package com.loitpcore.function.notification.notifications

import androidx.core.app.NotificationCompat

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class InboxNotification(
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
        setInboxStyle(builder)
        return builder
    }

    private fun setBuilderIcon(builder: NotificationCompat.Builder) {
        icon?.let {
            builder.setSmallIcon(it)
        }
    }

    private fun setInboxStyle(builder: NotificationCompat.Builder) {
        val style = NotificationCompat.InboxStyle()
        for (item in inboxItems) {
            style.addLine(item)
        }
        inboxSummary?.let {
            style.setSummaryText(it)
        }
        builder.setStyle(style)
    }
}
