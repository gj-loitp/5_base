package vn.loitp.app.activity.demo.floatingvideo

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_demo_floating_video.*
import vn.loitp.app.R

@LogTag("FloatingWidgetActivity")
@IsFullScreen(false)
class FloatingWidgetActivity : BaseFontActivity() {

    companion object {
        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION_SHOW = 2084
        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION_EDGE = 2085
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_floating_video
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btShow.setSafeOnClickListener {
            handleShow()
        }
        btEdge.setSafeOnClickListener {
            handleEdge()
        }
    }

    private fun handleShow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this@FloatingWidgetActivity)) {
            LDialogUtil.showDialog2(
                    context = this,
                    title = "Permission",
                    msg = "Plz open overlay permisson",
                    button1 = "Yes",
                    button2 = "No",
                    onClickButton1 = {
                        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                        startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION_SHOW)
                    },
                    onClickButton2 = {

                    }
            )
        } else {
            startService(Intent(this, FloatingViewVideoService::class.java))
            onBackPressed()
        }
    }

    private fun handleEdge() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this@FloatingWidgetActivity)) {
            LDialogUtil.showDialog2(
                    context = this,
                    title = "Permission",
                    msg = "Plz open overlay permisson",
                    button1 = "Yes",
                    button2 = "No",
                    onClickButton1 = {
                        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                        startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION_EDGE)
                    },
                    onClickButton2 = {

                    }
            )
        } else {
            startService(Intent(this, FloatingViewEdgeService::class.java))
            onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION_SHOW) {
            handleShow()
        } else if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION_EDGE) {
            handleEdge()
        }
    }

}
