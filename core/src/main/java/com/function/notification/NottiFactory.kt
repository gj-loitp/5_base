package com.function.notification

import com.function.notification.notifications.*

object NottiFactory {

    enum class TYPE {
        INBOX,
        BIG_TEXT,
        BIG_PICTURE,
        STANDARD
    }

    operator fun get(
            type: TYPE?,
            title: String?,
            content: String?
    ): CustomNotification {

        return when (type) {
            TYPE.STANDARD -> {
                StandardNotification(title, content)
            }
            TYPE.BIG_TEXT -> {
                BIgTextNotification(title, content)
            }
            TYPE.BIG_PICTURE -> {
                BigPictureNotification(title, content)
            }
            TYPE.INBOX -> {
                InboxNotification(title, content)
            }
            else -> {
                StandardNotification(title, content)
            }
        }
    }

}
