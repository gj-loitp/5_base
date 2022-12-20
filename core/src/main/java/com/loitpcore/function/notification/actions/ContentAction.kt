package com.loitpcore.function.notification.actions

import android.app.PendingIntent
import android.content.Context
import android.content.Intent

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ContentAction : NotificationAction {
    constructor(
        intent: Intent?,
        context: Context?
    ) : super(intent, context)

    constructor(pendingIntent: PendingIntent?) : super(pendingIntent)
}
