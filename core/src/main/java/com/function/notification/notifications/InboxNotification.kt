package com.function.notification.notifications

import androidx.core.app.NotificationCompat

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

    fun setBuilderIcon(builder: NotificationCompat.Builder) {
        icon?.let {
            builder.setSmallIcon(it)
        }
    }

    fun setInboxStyle(builder: NotificationCompat.Builder) {
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
