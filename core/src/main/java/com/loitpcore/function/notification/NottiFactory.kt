package com.loitpcore.function.notification

import com.loitpcore.function.notification.notifications.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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
