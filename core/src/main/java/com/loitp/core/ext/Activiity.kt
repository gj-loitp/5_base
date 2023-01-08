package com.loitp.core.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.Settings
import android.provider.Telephony
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.loitp.R
import com.loitp.core.base.BaseActivity
import com.loitp.core.utils.AppUtils
import com.loitp.core.utils.FragmentUtils

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
                Intent.ACTION_VIEW, Uri.parse(url)
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
    val calendarUri = CalendarContract.CONTENT_URI.buildUpon().appendPath("time").build()
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
                Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")
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
        var sAux = "\nỨng dụng này rất bổ ích, thân mời bạn tải về cài đặt để trải nghiệm\n\n"
        sAux = sAux + "https://play.google.com/store/apps/details?id=" + this.packageName
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

fun Context.getFacebookPageURL(): String {
    val facebookUrl = "https://www.facebook.com/hoidammedocsach"
    val facebookPageId = "hoidammedocsach"
    val packageManager = this.packageManager
    return try {
        val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
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
        this.showDialog1(
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
            this.showDialog1(
                title = this.getString(R.string.err),
                msg = this.getString(R.string.cannot_find_messenger_app),
                button1 = this.getString(R.string.ok)
            )
        }
    }
}

// playYoutube(activity, "http://www.youtube.com/watch?v=Hxy8BZGQ5Jo");
fun Activity.playYoutube(
    url: String?
) {
    if (url.isNullOrEmpty()) {
        return
    }
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    this.tranIn()
}

fun Activity.playYoutubeWithId(
    id: String
) {
    this.playYoutube(url = "http://www.youtube.com/watch?v=$id")
}

fun Activity.setChangeStatusBarTintToDark(
    shouldChangeStatusBarTintToDark: Boolean
) {
    val decor = this.window.decorView
    if (shouldChangeStatusBarTintToDark) {
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        // We want to change tint color to white again.
        // You can also record the flags in advance so that you can turn UI back completely if
        // you have set other flags before, such as translucent or full screen.
        decor.systemUiVisibility = 0
    }
}

val screenWidth: Int
    get() = Resources.getSystem().displayMetrics.widthPixels

val screenHeight: Int
    get() = Resources.getSystem().displayMetrics.heightPixels

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = this.resources.getIdentifier(
        "status_bar_height", "dimen", "android"
    )
    if (resourceId > 0) {
        result = this.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Context.getBottomBarHeight(): Int {
    val hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey()
    val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

    if (!hasMenuKey && !hasBackKey) {
        // Do whatever you need to do, this device has a navigation bar
        var result = 0
        val resourceId = this.resources.getIdentifier(
            "design_bottom_navigation_height", "dimen", this.packageName
        )
        if (resourceId > 0) {
            result = LAppResource.application.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    return 0
}

fun Context.getScreenHeightIncludeNavigationBar(): Int {
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val outPoint = Point()
    // include navigation bar
    display.getRealSize(outPoint)
    val mRealSizeHeight: Int = if (outPoint.y > outPoint.x) {
        outPoint.y
        // mRealSizeWidth = outPoint.x;
    } else {
        outPoint.x
        // mRealSizeWidth = outPoint.y;
    }
    return mRealSizeHeight
}

@SuppressLint("ObsoleteSdkInt")
fun Activity.showStatusBar(
) {
    if (Build.VERSION.SDK_INT < 16) {
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    } else {
        val decorView = this.window.decorView
        // Show Status Bar.
        val uiOptions = View.SYSTEM_UI_FLAG_VISIBLE
        decorView.systemUiVisibility = uiOptions
    }
}

@SuppressLint("ObsoleteSdkInt")
fun Activity.hideStatusBar(
) {
    // Hide Status Bar
    if (Build.VERSION.SDK_INT < 16) {
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    } else {
        val decorView = this.window.decorView
        // Hide Status Bar.
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
    }
}

fun Activity.toggleFullscreen(
) {
    val attrs = this.window.attributes
    attrs.flags = attrs.flags xor WindowManager.LayoutParams.FLAG_FULLSCREEN
    // attrs.flags ^= WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
    // attrs.flags ^= WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
    this.window.attributes = attrs
    /*if (isFullScreen(activity)) {
        hideNavigationBar(activity)
    } else {
        showNavigationBar(activity)
    }*/
}

fun Activity.toggleFullscreen(
    isFullScreen: Boolean
) {
    if (isFullScreen) {
        this.window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    } else {
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}

fun Activity.hideNavigationBar(
) {
    // set navigation bar status, remember to disable "setNavigationBarTintEnabled"
    val flags =
        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    // This work only for android 4.4+
    this.window.decorView.systemUiVisibility = flags

    // Code below is to handle presses of Volume up or Volume down.
    // Without this, after pressing volume buttons, the navigation bar will
    // show up and won't hide
    val decorView = this.window.decorView
    decorView.setOnSystemUiVisibilityChangeListener { visibility ->
        if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
            decorView.systemUiVisibility = flags
        }
    }
}

fun Activity.showNavigationBar(
) {
    // set navigation bar status, remember to disable "setNavigationBarTintEnabled"
    val flags =
        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    // This work only for android 4.4+
    this.window.decorView.systemUiVisibility = flags

    // Code below is to handle presses of Volume up or Volume down.
    // Without this, after pressing volume buttons, the navigation bar will
    // show up and won't hide
    val decorView = this.window.decorView
    decorView.setOnSystemUiVisibilityChangeListener { visibility ->
        if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
            decorView.systemUiVisibility = flags
        }
    }
}

@SuppressLint("ObsoleteSdkInt")
fun Activity.hideDefaultControls(
) {
    val window = this.window ?: return
    window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    val decorView = window.decorView
    var uiOptions = decorView.systemUiVisibility
    if (Build.VERSION.SDK_INT >= 14) {
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
    }
    if (Build.VERSION.SDK_INT >= 16) {
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    if (Build.VERSION.SDK_INT >= 19) {
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
    decorView.systemUiVisibility = uiOptions
}

@SuppressLint("ObsoleteSdkInt")
fun Activity.showDefaultControls(
) {
    val window = this.window ?: return
    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    val decorView = window.decorView
    var uiOptions = decorView.systemUiVisibility
    if (Build.VERSION.SDK_INT >= 14) {
        uiOptions = uiOptions and View.SYSTEM_UI_FLAG_LOW_PROFILE.inv()
    }
    if (Build.VERSION.SDK_INT >= 16) {
        uiOptions = uiOptions and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
    }
    if (Build.VERSION.SDK_INT >= 19) {
        uiOptions = uiOptions and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
    }
    decorView.systemUiVisibility = uiOptions
}

// rotate screen
fun Activity.setScreenOrientation(
    isPortrait: Boolean = true
) {
    if (isPortrait) {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    } else {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}

fun Activity.replaceFragment(
    containerFrameLayoutIdRes: Int, fragment: Fragment, isAddToBackStack: Boolean
) {
    if (this is BaseActivity) {
        val transaction = this.supportFragmentManager.beginTransaction()
        // transaction.setCustomAnimations(android.R.anim.fade_in, 0);
        transaction.replace(containerFrameLayoutIdRes, fragment)
        if (isAddToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}

fun Activity.addFragment(
    containerFrameLayoutIdRes: Int, fragment: Fragment, isAddToBackStack: Boolean
) {
    if (this is BaseActivity) {
        val transaction = this.supportFragmentManager.beginTransaction()
        // transaction.setCustomAnimations(android.R.anim.fade_in, 0)
        transaction.add(containerFrameLayoutIdRes, fragment)
        if (isAddToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}

fun Activity.addFragment(
    containerFrameLayoutIdRes: Int, fragment: Fragment, tag: String, isAddToBackStack: Boolean
) {
    if (this is BaseActivity) {
        val transaction = this.supportFragmentManager.beginTransaction()
        // transaction.setCustomAnimations(android.R.anim.fade_in, 0)
        transaction.add(containerFrameLayoutIdRes, fragment, tag)
        if (isAddToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}

fun Activity.findFragmentByTag(
    tag: String
): Fragment? {
    if (this is BaseActivity) {
        return this.supportFragmentManager.findFragmentByTag(tag)
    }
    return null
}

@Suppress("unused")
fun Activity.removeFragmentByTag(
    tag: String
) {
    val fragment = this.findFragmentByTag(tag = tag)
    if (fragment != null) {
        val transaction = (this as BaseActivity).supportFragmentManager.beginTransaction()
        // transaction.setCustomAnimations(0, android.R.anim.fade_out)
        transaction.remove(fragment).commit()
    }
}

@Suppress("unused")
fun Activity.removeAllFragments(
) {
    if (this is BaseActivity) {
        FragmentUtils.removeAllFragments(this.supportFragmentManager)
    }
}

fun Context.isLandscape(): Boolean {
    return when ((this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation) {
        Surface.ROTATION_0 -> false
        Surface.ROTATION_90 -> true
        Surface.ROTATION_180 -> false
        else -> true
    }
}

// from 0 to 100
fun Context?.setBrightness(
    value: Int
) {
    if (this == null) {
        return
    }

    val isCanWriteSystem = checkSystemWritePermission()

    if (!isCanWriteSystem) {
        this.showDialog1(
            title = "Thông báo",
            msg = "Ứng dụng cần bạn cần cấp quyền điều chỉnh độ sáng màn hình",
            button1 = "Cấp phép",
            onClickButton1 = {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:" + this.packageName)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                this.startActivity(intent)
                this.tranIn()
            })
        return
    }

    // constrain the value of brightness
    val brightness: Int = when {
        value < 0 -> 0
        value > 100 -> 100
        else -> value * 255 / 100
    }

    try {
        // sets manual parrallaxMode and brightnes 255
        Settings.System.putInt(
            this.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        ) // this will set the manual parrallaxMode (set the automatic parrallaxMode off)
        Settings.System.putInt(
            this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness
        ) // this will set the brightness to maximum (255)

        // refreshes the screen
        val br = Settings.System.getInt(
            this.contentResolver, Settings.System.SCREEN_BRIGHTNESS
        )
        val lp = (this as Activity).window.attributes
        lp.screenBrightness = br.toFloat() / 255
        this.window.attributes = lp
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.getCurrentBrightness(): Int {
    return try {
        Settings.System.getInt(
            this.contentResolver, Settings.System.SCREEN_BRIGHTNESS
        )
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

fun Context.checkSystemWritePermission(): Boolean {
    return Settings.System.canWrite(this)
}

fun Activity.showPopup(
    showOnView: View,
    menuRes: Int,
    callback: ((MenuItem) -> Unit)? = null,
) {
    val popup = PopupMenu(this, showOnView)
    popup.menuInflater.inflate(menuRes, popup.menu)
    popup.setOnMenuItemClickListener { menuItem ->
        callback?.invoke(menuItem)
        true
    }
    popup.show()
}
