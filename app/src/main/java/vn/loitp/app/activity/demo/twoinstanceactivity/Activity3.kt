package vn.loitp.app.activity.demo.twoinstanceactivity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_3.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_3)
@LogTag("Activity3")
@IsFullScreen(false)
class Activity3 : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logD("suzuki onCreate")

        btGoTo1.setSafeOnClickListener {
            val intent = Intent(this, Activity1::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btGoTo2.setSafeOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
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

}