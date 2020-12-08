package vn.loitp.app.activity.demo.floatingwidget

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import kotlinx.android.synthetic.main.activity_demo_floating_widget.*
import vn.loitp.app.R

@LogTag("FloatingWidgetActivity")
@IsFullScreen(false)
class FloatingWidgetActivity : BaseFontActivity() {

    companion object {
        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_floating_widget
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btNotifyMe.setOnClickListener {
            startService()
        }
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            LDialogUtil.showDialog2(
                    context = this,
                    title = "Permission",
                    msg = "Please open overlay permission",
                    button1 = "Yes",
                    button2 = "No",
                    onClickButton1 = {
                        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                        startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
                    },
                    onClickButton2 = {

                    }
            )
        } else {
            startService(Intent(this, FloatingViewService::class.java))
            onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            startService()
        }
    }
}
