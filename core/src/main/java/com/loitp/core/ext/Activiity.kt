package com.loitp.core.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.ContentUris
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.Telephony
import android.view.WindowManager
import com.loitp.R
import com.loitp.core.utilities.LDialogUtil
import com.loitp.core.utils.AppUtils

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
    this.tranIn()
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
        this.tranIn()
    } catch (ex: Exception) {
        ex.printStackTrace()
        this.moreApp()
    }
}

//mo app dong ho mac dinh cua device
fun Activity.launchClockApp() {
    try {
        val i = Intent(AlarmClock.ACTION_SHOW_ALARMS)
        this.startActivity(i)
        this.tranIn()
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
    this.tranIn()
}

//go mot app bat ky nao do
fun Activity.uninstallApp(
    packageName: String
) {
    val intent = Intent(Intent.ACTION_DELETE)
    intent.data = Uri.parse("package:$packageName")
    this.startActivity(intent)
    this.tranIn()
}

fun Activity.toggleFullScreen() {
    val attrs = this.window.attributes
    attrs.flags = attrs.flags xor WindowManager.LayoutParams.FLAG_FULLSCREEN
    this.window.attributes = attrs
}

@SuppressLint("SourceLockedOrientationActivity")
fun Activity.toggleScreenOrientation() {
    val s = getScreenOrientation()
    if (s == Configuration.ORIENTATION_LANDSCAPE) {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
    } else if (s == Configuration.ORIENTATION_PORTRAIT) {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    }
}

@SuppressLint("SourceLockedOrientationActivity")
fun Activity.changeScreenPortrait() {
    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

fun Activity.changeScreenLandscape() {
    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

fun Activity.getScreenOrientation(): Int {
    return this.resources.configuration.orientation
}

@Suppress("unused")
fun Activity.setSoftInputMode(
    mode: Int
) {
    this.window.setSoftInputMode(mode)
}

// https://gist.github.com/mustafasevgi/8c6b638ffd5fca90d45d
fun Activity?.sendSMS(
    text: String
) {
    if (this == null) {
        return
    }
    val defaultSmsPackageName =
        Telephony.Sms.getDefaultSmsPackage(this) // Need to change the build to API 19

    val sendIntent = Intent(Intent.ACTION_SEND)
    sendIntent.type = "text/plain"
    sendIntent.putExtra(Intent.EXTRA_TEXT, text)

    if (defaultSmsPackageName != null)
    // Can be null in case that there is no default, then the user would be able to choose
    // any app that support this intent.
    {
        sendIntent.setPackage(defaultSmsPackageName)
    }
    this.startActivity(sendIntent)
    this.tranIn()
}

fun Activity.rateApp(
    packageName: String = AppUtils.appPackageName
) {
    try {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            )
        )
        this.tranIn()
    } catch (e: android.content.ActivityNotFoundException) {
        e.printStackTrace()
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
            )
        )
        this.tranIn()
    }
}

fun Activity.moreApp(
    nameOfDeveloper: String = "Roy93Group"
) {
    val uri = "https://play.google.com/store/apps/developer?id=$nameOfDeveloper"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    this.startActivity(intent)
    this.tranIn()
}

fun Activity.shareApp(
) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, this.getString(R.string.app_name))
        var sAux =
            "\nỨng dụng này rất bổ ích, thân mời bạn tải về cài đặt để trải nghiệm\n\n"
        sAux =
            sAux + "https://play.google.com/store/apps/details?id=" + this.packageName
        intent.putExtra(Intent.EXTRA_TEXT, sAux)
        this.startActivity(Intent.createChooser(intent, "Vui lòng chọn"))
        this.tranIn()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Activity.share(
    msg: String
) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, this.getString(R.string.app_name))
        // String sAux = "\nỨng dụng này rất bổ ích, thân mời bạn tải về cài đặt để trải nghiệm\n\n";
        // sAux = sAux + "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        this.startActivity(Intent.createChooser(intent, "Share via"))
        this.tranIn()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// like fanpage
fun Activity?.likeFacebookFanpage(
) {
    this?.apply {
        val facebookIntent = Intent(Intent.ACTION_VIEW)
        val facebookUrl = getFacebookPageURL()
        facebookIntent.data = Uri.parse(facebookUrl)
        startActivity(facebookIntent)
        tranIn()
    }
}

fun getFacebookPageURL(): String {
    val facebookUrl = "https://www.facebook.com/hoidammedocsach"
    val facebookPageId = "hoidammedocsach"
    val packageManager = LAppResource.application.packageManager
    return try {
        val versionCode =
            packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
        if (versionCode >= 3002850) {
            "fb://facewebmodal/f?href=$facebookUrl"
        } else {
            "fb://page/$facebookPageId"
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        facebookUrl
    }
}

/*
chat with fanpage Thugiannao
 */
fun Activity.chatMessenger(
) {
    val packageManager = this.packageManager
    var isFBInstalled = false
    try {
        val versionCode = packageManager.getPackageInfo("com.facebook.orca", 0).versionCode
        if (versionCode >= 0) isFBInstalled = true
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    if (!isFBInstalled) {
        LDialogUtil.showDialog1(
            context = this,
            title = this.getString(R.string.err),
            msg = this.getString(R.string.cannot_find_messenger_app),
            button1 = this.getString(R.string.ok)
        )
    } else {
        var uri = Uri.parse("fb-messenger://user/")
        uri = ContentUris.withAppendedId(uri, java.lang.Long.valueOf("947139732073591"))
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            this.startActivity(intent)
            this.tranIn()
        } catch (e: Exception) {
            e.printStackTrace()
            LDialogUtil.showDialog1(
                context = this,
                title = this.getString(R.string.err),
                msg = this.getString(R.string.cannot_find_messenger_app),
                button1 = this.getString(R.string.ok)
            )
        }
    }
}
