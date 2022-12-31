package com.loitp.views.lDebugView

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.loitp.core.utils.ServiceUtils
import com.loitp.views.lDebugView.LComunicateDebug.postFromActivity

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object LDebug {

    private fun isNeedPermission(activity: Activity): Boolean {
        return !Settings.canDrawOverlays(activity)
    }

    @JvmStatic
    fun init(
        activity: Activity,
        data: ((Intent) -> Unit)? = null,
    ) {
        if (isNeedPermission(activity)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + activity.packageName)
            )
            data?.invoke(intent)
        } else {
            start()
        }
    }

    @JvmStatic
    fun start() {
        ServiceUtils.startService(LDebugViewService::class.java.name)
    }

    @JvmStatic
    fun stop() {
        ServiceUtils.stopService(LDebugViewService::class.java.name)
    }

    fun log(log: String?) {
        postFromActivity(
            msg = LComunicateDebug.MsgFromActivity(
                type = LComunicateDebug.MsgFromActivity.TYPE_D,
                msg = log,
                any = null
            )
        )
    }

    fun log(
        type: Int,
        log: String?
    ) {
        postFromActivity(
            msg = LComunicateDebug.MsgFromActivity(
                type = type,
                msg = log,
                any = null
            )
        )
    }

    fun log(o: Any?) {
        postFromActivity(
            msg = LComunicateDebug.MsgFromActivity(
                type = LComunicateDebug.MsgFromActivity.TYPE_D,
                msg = "",
                any = o
            )
        )
    }

    fun log(
        type: Int,
        o: Any?
    ) {
        postFromActivity(
            msg = LComunicateDebug.MsgFromActivity(
                type = type,
                msg = "",
                any = o
            )
        )
    }
}
