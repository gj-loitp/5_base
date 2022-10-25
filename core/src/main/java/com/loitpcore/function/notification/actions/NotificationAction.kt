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
open class NotificationAction {
    var text: String? = null
    var intent: Intent? = null
    var image = 0
    var context: Context? = null
    private var pendingIntent: PendingIntent? = null
    private val requestCode: Int = 0

    constructor(intent: Intent?, context: Context?) {
        this.intent = intent
        this.context = context
    }

    constructor(text: String?, intent: Intent?, context: Context?) {
        this.text = text
        this.intent = intent
        this.context = context
    }

    constructor(text: String?, intent: Intent?, image: Int, context: Context?) {
        this.text = text
        this.intent = intent
        this.image = image
        this.context = context
    }

    constructor(text: String?, pendingIntent: PendingIntent?, image: Int) {
        this.text = text
        this.pendingIntent = pendingIntent
        this.image = image
    }

    constructor(pendingIntent: PendingIntent?) {
        this.pendingIntent = pendingIntent
    }

    fun getPendingIntent(): PendingIntent {
        pendingIntent?.let {
            return it
        }
//        return PendingIntent.getActivity(context, requestCode, intent, 0)
        return PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}
