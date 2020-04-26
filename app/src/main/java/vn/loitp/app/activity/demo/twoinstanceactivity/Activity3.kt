package vn.loitp.app.activity.demo.twoinstanceactivity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_3.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.loitp.app.R
import vn.loitp.app.activity.demo.twoinstanceactivity.FloatingViewService.MessageEvent

class Activity3 : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logD("suzuki onCreate")

        btGoTo1.setOnClickListener {
            val intent = Intent(activity, Activity1::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btGoTo2.setOnClickListener {
            val intent = Intent(activity, Activity2::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            askPermission()
        }
        btGoToFloat.setOnClickListener {
            when {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {
                    startService(Intent(activity, FloatingViewService::class.java))
                    //finish();
                }
                Settings.canDrawOverlays(activity) -> {
                    startService(Intent(activity, FloatingViewService::class.java))
                    //finish();
                }
                else -> {
                    askPermission()
                    showShort("You need System Alert Window Permission to do this")
                }
            }
        }
    }

    private fun askPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        logD("onNewIntent")
        if (intent.flags or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT > 0) {
            mIsRestoredToTop = true
        }
    }

    private var mIsRestoredToTop = false
    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT >= 19 && !isTaskRoot && mIsRestoredToTop) {
            // 4.4.2 platform issues for FLAG_ACTIVITY_REORDER_TO_FRONT,
            // reordered activity back press will go to home unexpectly,
            // Workaround: move reordered activity current task to front when it's finished.
            val tasksManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            tasksManager.moveTaskToFront(taskId, ActivityManager.MOVE_TASK_NO_USER_ACTION)
        }
    }

    override fun onDestroy() {
        logD("onDestroy")
        super.onDestroy()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_3
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent?) {
        logD("onMessageEvent")
        val intent = Intent(this@Activity3, Activity2::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(intent)
    }

    companion object {
        private const val SYSTEM_ALERT_WINDOW_PERMISSION = 2084
    }
}