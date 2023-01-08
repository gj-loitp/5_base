package com.loitp.core.ext

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import com.daimajia.androidanimations.library.Techniques
import com.loitp.R
import com.loitp.core.common.Constants
import com.loitp.core.helper.fbComment.FbCommentActivity
import com.loitp.data.ActivityData
import com.loitp.views.dlg.slideImages.LSlideAdapter
import com.loitp.views.loading.window.WP10ProgressBar
import de.cketti.mailto.EmailIntentBuilder

//check xem app hien tai co phai la default launcher hay khong
fun Context.isDefaultLauncher(): Boolean {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    val resolveInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.resolveActivity(
            intent,
            PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
        )
    } else {
        @Suppress("DEPRECATION")
        packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
    }

    val currentLauncherName = resolveInfo?.activityInfo?.packageName
    if (currentLauncherName == packageName) {
        return true
    }
    return false
}

//mo app setting default cua device
fun Context.launchSystemSetting(
    packageName: String
) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:$packageName")
    this.startActivity(intent)
    tranIn()
}

fun Context.tranIn() {
    if (this !is Activity) {
        return
    }
    when (ActivityData.instance.type) {
        Constants.TYPE_ACTIVITY_TRANSITION_NO_ANIM -> {
            transActivityNoAnimation()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT -> {
            // do nothing
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT -> {
            slideLeft()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_RIGHT -> {
            slideRight()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_DOWN -> {
            slideDown()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_UP -> {
            slideUp()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_FADE -> {
            fade()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_ZOOM -> {
            zoom()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL -> {
            windmill()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL -> {
            diagonal()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SPIN -> {
            spin()
        }
    }
}

fun Context.tranOut() {
    if (this !is Activity) {
        return
    }
    when (ActivityData.instance.type) {
        Constants.TYPE_ACTIVITY_TRANSITION_NO_ANIM -> {
            transActivityNoAnimation()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT -> {
            // do nothing
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_LEFT -> {
            slideRight()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_RIGHT -> {
            slideLeft()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_DOWN -> {
            slideUp()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SLIDE_UP -> {
            slideDown()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_FADE -> {
            fade()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_ZOOM -> {
            zoom()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_WINDMILL -> {
            windmill()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_DIAGONAL -> {
            diagonal()
        }
        Constants.TYPE_ACTIVITY_TRANSITION_SPIN -> {
            spin()
        }
    }
}

fun Context.transActivityNoAnimation() {
    if (this is Activity) {
        this.overridePendingTransition(0, 0)
    }
}

fun Context.slideLeft() {
    if (this is Activity) {
        this.overridePendingTransition(
            R.anim.l_slide_left_enter,
            R.anim.l_slide_left_exit
        )
    }
}

fun Context.slideRight() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_slide_in_left, R.anim.l_slide_out_right)
    }
}

fun Context.slideDown() {
    if (this is Activity) {
        this.overridePendingTransition(
            R.anim.l_slide_down_enter,
            R.anim.l_slide_down_exit
        )
    }
}

fun Context.slideUp() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_slide_up_enter, R.anim.l_slide_up_exit)
    }
}

fun Context.zoom() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_zoom_enter, R.anim.l_zoom_exit)
    }
}

fun Context.fade() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_fade_enter, R.anim.l_fade_exit)
    }
}

fun Context.windmill() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_windmill_enter, R.anim.l_windmill_exit)
    }
}

fun Context.spin() {
    if (this is Activity) {
        this.overridePendingTransition(R.anim.l_spin_enter, R.anim.l_spin_exit)
    }
}

fun Context.diagonal() {
    if (this is Activity) {
        this.overridePendingTransition(
            R.anim.l_diagonal_right_enter,
            R.anim.l_diagonal_right_exit
        )
    }
}

/*
         * send email support
         */
fun Context?.sendEmail(
) {
    val emailIntent = Intent(Intent.ACTION_SENDTO)
    emailIntent.data = Uri.parse("mailto: www.muathu@gmail.com")
    this?.startActivity(Intent.createChooser(emailIntent, "Send feedback"))
}

fun Context.openBrowserPolicy(
) {
    this.openUrlInBrowser(url = Constants.URL_POLICY)
}

fun Context?.openUrlInBrowser(
    url: String?
) {
    if (this == null || url.isNullOrEmpty()) {
        return
    }
    val defaultBrowser =
        Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
    defaultBrowser.data = Uri.parse(url)
    this.startActivity(defaultBrowser)
    this.tranIn()
}

fun Context?.openFacebookComment(
    url: String? = null,
) {
    if (this == null || url.isNullOrEmpty()) {
        return
    }
    val intent = Intent(this, FbCommentActivity::class.java)
    intent.putExtra(Constants.FACEBOOK_COMMENT_URL, url)
    this.startActivity(intent)
    this.tranIn()
}

fun Context.sendEmail(
    to: String,
    cc: String? = null,
    bcc: String? = null,
    subject: String? = null,
    body: String? = null,
) {
    val i = EmailIntentBuilder.from(this)
    i.to(to)
    cc?.let {
        i.cc(it)
    }
    bcc?.let {
        i.bcc(it)
    }
    subject?.let {
        i.subject(it)
    }
    body?.let {
        i.body(it)
    }
    i.start()
}

fun Context.showNotification(
    title: String,
    body: String,
    iconRes: Int,
    notificationIntent: Intent
) {
    if (title.isEmpty() || body.isEmpty()) {
        return
    }

    val channelId = "CHANNEL_ID"
    val channelName = "CHANNEL_NAME"

    val requestID = System.currentTimeMillis().toInt()
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            requestID,
//            notificationIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
    val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getActivity(
            this,
            requestID,
            notificationIntent,
            PendingIntent.FLAG_MUTABLE
        )
    } else {
        PendingIntent.getActivity(
            this,
            requestID,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    val notificationManager =
        this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(
        this,
        channelId
    )
        .setSmallIcon(iconRes)
        .setAutoCancel(true)
        .setSound(defaultSoundUri)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
        .setContentIntent(pendingIntent)
        .setContentTitle(title)
        .setContentText(body)

    notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
}

fun Context.showDialog1(
    title: String? = null,
    msg: String? = null,
    button1: String = getString(R.string.confirm),
    onClickButton1: ((Unit) -> Unit)? = null
): AlertDialog {
    val builder = if (isDarkTheme()) {
        AlertDialog.Builder(ContextThemeWrapper(this, R.style.DarkAlertDialogCustom))
    } else {
        AlertDialog.Builder(ContextThemeWrapper(this, R.style.LightAlertDialogCustom))
    }

    if (title.isNullOrEmpty()) {
        // do nothing
    } else {
        builder.setTitle(title)
    }
    if (msg.isNullOrEmpty()) {
        // do nothing
    } else {
        builder.setMessage(msg)
    }

    builder.setPositiveButton(button1) { _, _ ->
        onClickButton1?.invoke(Unit)
    }
    val dialog = builder.create()
    dialog.show()

    if (isDarkTheme()) {
        val color = getColor(R.color.white)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
    } else {
        val color = getColor(R.color.colorPrimary)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
    }

    return dialog
}

fun Context.showDialog2(
    title: String? = null,
    msg: String? = null,
    button1: String = this.getString(R.string.confirm),
    button2: String = this.getString(R.string.cancel),
    onClickButton1: ((Unit) -> Unit)? = null,
    onClickButton2: ((Unit) -> Unit)? = null
): AlertDialog {
    val builder = if (isDarkTheme()) {
        AlertDialog.Builder(ContextThemeWrapper(this, R.style.DarkAlertDialogCustom))
    } else {
        AlertDialog.Builder(ContextThemeWrapper(this, R.style.LightAlertDialogCustom))
    }
    if (title.isNullOrEmpty()) {
        // do nothing
    } else {
        builder.setTitle(title)
    }
    if (msg.isNullOrEmpty()) {
        // do nothing
    } else {
        builder.setMessage(msg)
    }
    builder.setNegativeButton(button1) { _, _ ->
        onClickButton1?.invoke(Unit)
    }
    builder.setPositiveButton(button2) { _, _ ->
        onClickButton2?.invoke(Unit)
    }
    val dialog = builder.create()
    dialog.show()
    if (isDarkTheme()) {
        val colorPrimary = getColor(R.color.white)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(colorPrimary)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorPrimary)
    } else {
        val colorPrimary = getColor(R.color.colorPrimary)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(colorPrimary)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorPrimary)
    }

    return dialog
}

fun Context.showDialog3(
    title: String? = null,
    msg: String? = null,
    button1: String? = null,
    button2: String? = null,
    button3: String? = null,
    onClickButton1: ((Unit) -> Unit)? = null,
    onClickButton2: ((Unit) -> Unit)? = null,
    onClickButton3: ((Unit) -> Unit)? = null
): AlertDialog {
    val builder = if (isDarkTheme()) {
        AlertDialog.Builder(ContextThemeWrapper(this, R.style.DarkAlertDialogCustom))
    } else {
        AlertDialog.Builder(ContextThemeWrapper(this, R.style.LightAlertDialogCustom))
    }
    if (title.isNullOrEmpty()) {
        // do nothing
    } else {
        builder.setTitle(title)
    }
    if (msg.isNullOrEmpty()) {
        // do nothing
    } else {
        builder.setMessage(msg)
    }
    if (button1.isNullOrEmpty()) {
        // do nothing
    } else {
        builder.setNegativeButton(button1) { _, _ ->
            onClickButton1?.invoke(Unit)
        }
    }
    if (button2.isNullOrEmpty()) {
        // do nothing
    } else {
        builder.setPositiveButton(button2) { _, _ ->
            onClickButton2?.invoke(Unit)
        }
    }
    if (button3.isNullOrEmpty()) {
        // do nothing
    } else {
        builder.setNeutralButton(button3) { _, _ ->
            onClickButton3?.invoke(Unit)
        }
    }

    val dialog = builder.create()
    dialog.show()
    if (isDarkTheme()) {
        val color = getColor(R.color.white)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(color)
    } else {
        val color = getColor(R.color.colorPrimary)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(color)
    }

    return dialog
}

fun Context.showDialogList(
    title: String? = null,
    arr: Array<String?>,
    onClick: ((Int) -> Unit)? = null
): AlertDialog {
    val builder = if (isDarkTheme()) {
        AlertDialog.Builder(ContextThemeWrapper(this, R.style.DarkAlertDialogCustom))
    } else {
        AlertDialog.Builder(ContextThemeWrapper(this, R.style.LightAlertDialogCustom))
    }
    if (title.isNullOrEmpty()) {
        // do nothing
    } else {
        builder.setTitle(title)
    }
    builder.setItems(arr) { _, which ->
        onClick?.invoke(which)
    }
    val dialog = builder.create()
    dialog.show()
    return dialog
}

// style ex ProgressDialog.STYLE_HORIZONTAL
@Suppress("DEPRECATION")
fun Context.showProgressDialog(
    max: Int,
    title: String,
    msg: String,
    isCancelAble: Boolean,
    style: Int,
    buttonTitle: String?,
    onClickButton1: ((Unit) -> Unit)? = null
): ProgressDialog {
    val progressDialog = ProgressDialog(this)
    progressDialog.max = max
    progressDialog.setMessage(msg)
    progressDialog.setCancelable(isCancelAble)
    progressDialog.setTitle(title)
    progressDialog.setProgressStyle(style)
    if (buttonTitle != null) {
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, buttonTitle) { _, _ ->
            onClickButton1?.invoke(Unit)
        }
    }
    progressDialog.show()
    return progressDialog
}

fun Dialog?.show() {
    if (this != null && !this.isShowing) {
        this.show()
    }
}

fun Dialog?.hide() {
    if (this != null && this.isShowing) {
        this.cancel()
    }
}

@SuppressLint("InflateParams")
fun Context?.genCustomProgressDialog(
): Dialog? {
    if (this == null || this !is Activity) {
        return null
    }
    val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.l_d_custom_progress)
    dialog.setCanceledOnTouchOutside(false)
    dialog.setCancelable(false)

    val progressBar = dialog.findViewById<WP10ProgressBar>(R.id.progressBar)
    progressBar.showProgressBar()

    dialog.window?.let {
        it.setBackgroundDrawable(ColorDrawable(getColor(R.color.black65)))

        val wlp = it.attributes
        wlp.gravity = Gravity.CENTER

        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()

        it.attributes = wlp
        it.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
    }
    return dialog
}

fun ProgressBar?.showProgress() {
    this?.isVisible = true
}

fun ProgressBar?.hideProgress() {
    this?.isVisible = false
}

fun Context.showDialogSlide(
    index: Int,
    imgList: List<String>,
    amount: Float,
    isShowController: Boolean,
    isShowIconClose: Boolean
): Dialog {
    val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.l_d_slide_images)
    dialog.setCanceledOnTouchOutside(true)
    val slideAdapter = LSlideAdapter(
        mContext = this,
        stringList = imgList,
        isShowIconClose = isShowIconClose,
        callback = object : LSlideAdapter.Callback {
            override fun onClickClose() {
                dialog.cancel()
            }
        })
    val viewPager = dialog.findViewById<ViewPager>(R.id.vp)
    viewPager.adapter = slideAdapter
    if (index != 0) {
        viewPager.currentItem = index
    }
    viewPager.setPullLikeIOSHorizontal()
    val ivNext = dialog.findViewById<ImageView>(R.id.ivNext)
    val ivPrev = dialog.findViewById<ImageView>(R.id.ivPrev)
    if (isShowController) {
        ivNext.visibility = View.VISIBLE
        ivPrev.visibility = View.VISIBLE
    } else {
        ivNext.visibility = View.INVISIBLE
        ivPrev.visibility = View.INVISIBLE
    }
    ivNext.setOnClickListener { view ->
        view.play(techniques = Techniques.Pulse)
        val next = viewPager.currentItem + 1
        if (next < imgList.size) {
            viewPager.currentItem = next
        }
    }
    ivPrev.setOnClickListener { view ->
        view.play(techniques = Techniques.Pulse)
        val prev = viewPager.currentItem - 1
        if (prev >= 0) {
            viewPager.currentItem = prev
        }
    }
    dialog.window?.let {
        // it.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        it.setBackgroundDrawable(ColorDrawable(getColor(R.color.black65)))
        it.setDimAmount(amount)

        val wlp = it.attributes
        wlp.gravity = Gravity.CENTER
        // wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        it.attributes = wlp
        it.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
    }
    dialog.show()
    return dialog
}
