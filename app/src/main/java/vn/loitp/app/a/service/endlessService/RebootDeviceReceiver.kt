package vn.loitp.app.a.service.endlessService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class RebootDeviceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED && getServiceState(context) == ServiceState.STARTED) {
            Intent(context, EndlessService::class.java).also {
                it.action = Actions.START.name
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    log("Starting the service in >=26 SDK_INT from a BroadcastReceiver")
                    context.startForegroundService(it)
                } else {
                    log("Starting the service in < 26 SDK_INT from a BroadcastReceiver")
                    context.startService(it)
                }
            }
        }
    }
}
