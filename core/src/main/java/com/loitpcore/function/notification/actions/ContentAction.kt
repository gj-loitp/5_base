package com.loitpcore.function.notification.actions

import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class ContentAction : NotificationAction {

    constructor(intent: Intent?, context: Context?) : super(intent, context)
    constructor(pendingIntent: PendingIntent?) : super(pendingIntent)
}
