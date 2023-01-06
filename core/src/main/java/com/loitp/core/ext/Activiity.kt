package com.loitp.core.ext

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.AlarmClock
import android.provider.CalendarContract
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LSocialUtil

//mo hop thoai de select launcher default
fun Activity.chooseLauncher(cls: Class<*>) {
    val componentName = ComponentName(this, cls)
    this.packageManager.setComponentEnabledSetting(
        /* p0 = */ componentName,
        /* p1 = */ PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        /* p2 = */ PackageManager.DONT_KILL_APP
    )
    val selector = Intent(Intent.ACTION_MAIN)
    selector.addCategory(Intent.CATEGORY_HOME)
    selector.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    this.startActivity(selector)
    LActivityUtil.tranIn(this)
    this.packageManager.setComponentEnabledSetting(
        /* p0 = */ componentName,
        /* p1 = */ PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
        /* p2 = */ PackageManager.DONT_KILL_APP
    )
}

//mo play store va search cac app ve icon
fun Activity.searchIconPack() {
    val url = "market://search?q=icon%20pack&c=apps"
    try {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
        LActivityUtil.tranIn(this)
    } catch (ex: Exception) {
        ex.printStackTrace()
        LSocialUtil.moreApp(this)
    }
}

//mo app dong ho mac dinh cua device
fun Activity.launchClockApp() {
    try {
        val i = Intent(AlarmClock.ACTION_SHOW_ALARMS)
        this.startActivity(i)
        LActivityUtil.tranIn(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

//mo app calendar mac dinh cua device
fun Activity.launchCalendar() {
    val calendarUri = CalendarContract.CONTENT_URI
        .buildUpon()
        .appendPath("time")
        .build()
    this.startActivity(Intent(Intent.ACTION_VIEW, calendarUri))
    LActivityUtil.tranIn(this)
}

//go mot app bat ky nao do
fun Activity.uninstallApp(
    packageName: String
) {
    val intent = Intent(Intent.ACTION_DELETE)
    intent.data = Uri.parse("package:$packageName")
    this.startActivity(intent)
    LActivityUtil.tranIn(this)
}
