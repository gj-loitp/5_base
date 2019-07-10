package vn.loitp.app.activity.customviews.layout.motionlayout

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import loitp.basemaster.R

@RequiresApi(Build.VERSION_CODES.LOLLIPOP) // for View#clipToOutline
class DemoMotionLayoutActivity : BaseFontActivity() {
    private lateinit var container: View

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return "TAG" + javaClass.simpleName;
    }

    override fun setLayoutResourceId(): Int {
        val layout = intent.getIntExtra("layout_file_id", R.layout.motion_01_basic)
        return layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LLog.d(TAG, "onCreate")
        //val layout = intent.getIntExtra("layout_file_id", R.layout.motion_01_basic)
        //setContentView(layout)
        container = findViewById(R.id.motionLayout)

        var layout = intent.getIntExtra("layout_file_id", R.layout.motion_01_basic);
        if (layout == R.layout.motion_11_coordinatorlayout) {
            val icon = findViewById<ImageView>(R.id.icon)
            icon?.clipToOutline = true
        }

        val doShowPaths = intent.getBooleanExtra("showPaths", false)
        (container as? MotionLayout)?.setShowPaths(doShowPaths)
    }

    fun changeState() {
        val motionLayout = container as? MotionLayout ?: return
        if (motionLayout.progress > 0.5f) {
            motionLayout.transitionToStart()
        } else {
            motionLayout.transitionToEnd()
        }
    }
}