package com.core.utilities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Telephony

/**
 * Created by www.muathu@gmail.com on 1/29/2018.
 */

object LSMSUtil {
    //https://gist.github.com/mustafasevgi/8c6b638ffd5fca90d45d
    fun sendSMS(activity: Activity?, text: String) {
        if (activity == null) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        // At least KitKat
        {
            val defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(activity) // Need to change the build to API 19

            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, text)

            if (defaultSmsPackageName != null)
            // Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName)
            }
            activity.startActivity(sendIntent)
        } else
        // For early versions, do what worked for you before.
        {
            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("sms:")
            sendIntent.putExtra("sms_body", text)
            activity.startActivity(sendIntent)
        }
        LActivityUtil.tranIn(activity)
    }
}
