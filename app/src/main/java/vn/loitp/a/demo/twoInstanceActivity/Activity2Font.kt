package vn.loitp.a.demo.twoInstanceActivity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.tranIn
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_2.*
import vn.loitp.R

@LogTag("Activity2")
@IsFullScreen(false)
class Activity2Font : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_2
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
            this.tvTitle?.text = Activity2Font::class.java.simpleName
        }
        btGoTo1.setSafeOnClickListener {
            val intent = Intent(this@Activity2Font, Activity1Font::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            this.tranIn()
        }
        btGoTo3.setSafeOnClickListener {
            val intent = Intent(this@Activity2Font, Activity3Font::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            this.tranIn()
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
