package vn.loitp.a.func.noti

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LNotification
import com.loitp.core.utilities.LUIUtil
import com.loitp.func.notification.Notti
import com.loitp.func.notification.NottiFactory
import com.loitp.func.notification.actions.ContentAction
import com.loitp.func.notification.actions.NotificationAction
import com.loitp.func.notification.config.LightSettings
import com.loitp.func.notification.config.NottiConfig
import com.loitp.func.notification.config.VibrationSettings
import kotlinx.android.synthetic.main.a_menu_notification.*
import vn.loitp.R

@LogTag("MenuNotificationActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuNotificationActivity : BaseFontActivity(), View.OnClickListener {

    companion object {
        const val KEY_NOTI_DATA_INTENT = "KEY_NOTI_DATA_INTENT"
    }

    private var notti: Notti? = null
    private val channelId = "my_package_channel"

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_notification
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuNotificationActivity::class.java.simpleName
        }
        val notiData = intent.getStringExtra(KEY_NOTI_DATA_INTENT)
        notiData?.let { d ->
            tvMenu.text = d
        }

        notti = Notti(
            this,
            NottiConfig(
                R.drawable.ic_launcher_loitp,
                VibrationSettings(*VibrationSettings.STD_VIBRATION),
                LightSettings(Color.BLUE)
            )
        )

        btSimpleNotification.setOnClickListener(this)
        btSimpleNotificationActions.setOnClickListener(this)
        btBigTextNotification.setOnClickListener(this)
        btInboxNotification.setOnClickListener(this)
        btBigPictureNotification.setOnClickListener(this)
        btNotificationHeadsup.setOnClickListener(this)

        goToNotificationSettings(this)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun goToNotificationSettings(context: Context) {
        val packageName = context.packageName
        try {
            var intent = Intent()
            when {
                Build.VERSION.SDK_INT > Build.VERSION_CODES.O -> {
                    // intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    // intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);
                    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent = Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS")
                    intent.putExtra("android.provider.extra.CHANNEL_ID", channelId)
                    intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName())
                }
                Build.VERSION.SDK_INT == Build.VERSION_CODES.O -> {
                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                    intent.putExtra("app_package", packageName)
                    intent.putExtra("app_uid", context.applicationInfo.uid)
                }
                Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT -> {
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.data = Uri.parse("package:$packageName")
                }
                else -> return
            }

            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {
        val intent: Intent?
        when (v) {
            btSimpleNotification -> {
                notti?.show(
                    NottiFactory[NottiFactory.TYPE.STANDARD, "some text", "some content"]
                        .setContentAction(
                            ContentAction(
                                Intent(
                                    this,
                                    MenuNotificationActivity::class.java
                                ),
                                this
                            )
                        )
                )
            }
            btSimpleNotificationActions -> {
                intent = Intent(this, MenuNotificationActivity::class.java)

                val actionsList = mutableListOf(
                    NotificationAction("action", intent, this),
                    NotificationAction("action 2", intent, this)
                )

                notti?.show(
                    NottiFactory[NottiFactory.TYPE.STANDARD, "some text", "some content"]
                        .setActions(actionsList)
                )
            }
            btBigTextNotification -> {
                notti?.show(
                    NottiFactory[NottiFactory.TYPE.BIG_TEXT, "some text", "some content"]
                        .setBigText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam posuere arcu enim, ut imperdiet sem pellentesque quis.Morbi in tempus lorem. Integer venenatis risus sit amet dolor lobortis, et consequat neque luctus. Etiam ut est nulla. Quisque turpis sapien, aliquet a consequat in, lacinia ut neque. Praesent scelerisque maximus nisi, sed pharetra nulla varius id. Proin at augue purus. Aliquam ut ullamcorper lorem, at vehicula nisl. Pellentesque imperdiet nunc vitae quam consectetur tempus. Nullam vel auctor orci. Ut a turpis ac quam placerat vestibulum. Sed ac hendrerit lorem, non imperdiet neque. Sed nisl urna, eleifend ac sem et, accumsan consectetur felis. Quisque cursus interdum erat, sit amet maximus felis consectetur ac. Aenean luctus, mi nec elementum bibendum, felis felis lacinia justo, vitae lacinia ligula nibh ut nulla. Nunc viverra commodo augue, in cursus nulla.")
                )
            }
            btInboxNotification -> {
                notti?.show(
                    NottiFactory[NottiFactory.TYPE.INBOX, "some text", "some content"]
                        .addInboxItem("some item")
                        .addInboxItem("another item")
                        .addInboxItem("and final item")
                        .setInboxSummary("random summary")
                )
            }
            btBigPictureNotification -> {
                val icon =
                    BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_loitp)
                val iconBig = BitmapFactory.decodeResource(this.resources, R.drawable.iv)
                notti?.show(
                    NottiFactory[NottiFactory.TYPE.BIG_PICTURE, "some text", "some " + "content"]
                        .setBigPicture(iconBig)
                        .setLargeIcon(icon)
                )
            }
            btNotificationHeadsup -> {
                val title = "This is title " + System.currentTimeMillis()
                val body = "This is body " + System.currentTimeMillis()
                val iconRes = R.drawable.ic_launcher_loitp
                val notificationIntent = Intent(this, MenuNotificationActivity::class.java)
                notificationIntent.putExtra(
                    KEY_NOTI_DATA_INTENT,
                    "KEY_NOTI_DATA_INTENT " + System.currentTimeMillis()
                )
                // pendingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                LNotification.showNotification(
                    title = title,
                    body = body,
                    iconRes = iconRes,
                    notificationIntent = notificationIntent
                )
            }
        }
    }
}
