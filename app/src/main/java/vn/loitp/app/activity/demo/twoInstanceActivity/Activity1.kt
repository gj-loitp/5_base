package vn.loitp.app.activity.demo.twoInstanceActivity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_1.*
import vn.loitp.app.R

@LogTag("Activity1")
@IsFullScreen(false)
class Activity1 : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_1
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = Activity1::class.java.simpleName
        }
        btGoTo2.setSafeOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btGoTo3.setSafeOnClickListener {
            val intent = Intent(this, Activity3::class.java)
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
        super.finish()//correct
        // 4.4.2 platform issues for FLAG_ACTIVITY_REORDER_TO_FRONT,
        // reordered activity back press will go to home unexpectly,
        // Workaround: move reordered activity current task to front when it's finished.
        val tasksManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        tasksManager.moveTaskToFront(taskId, ActivityManager.MOVE_TASK_NO_USER_ACTION)
    }

    override fun onDestroy() {
        logD("onDestroy")
        super.onDestroy()
    }
}
