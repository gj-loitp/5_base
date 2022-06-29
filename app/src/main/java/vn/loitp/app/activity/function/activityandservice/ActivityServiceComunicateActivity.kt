package vn.loitp.app.activity.function.activityandservice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_func_service_communicate.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.loitp.app.R
import vn.loitp.app.activity.demo.floatingwidget.CommunicateMng

@LogTag("ActivityServiceComunicateActivity")
@IsFullScreen(false)
class ActivityServiceComunicateActivity : BaseFontActivity() {

    companion object {
        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_service_communicate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btNotifyMe.setSafeOnClickListener {
            handleNotify()
        }
        bt0.setSafeOnClickListener {
            CommunicateMng.postFromActivity(CommunicateMng.MsgFromActivity(bt0.text.toString()))
        }
    }

    private fun handleNotify() {
        // Check if the application has draw over other apps permission or not?
        // This permission is by default available for API<23. But for API > 23
        // you have to ask for the permission in runtime.
        // If the draw over permission is not available open the settings screen
        // to grant the permission.
        val intent =
            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
        startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            handleNotify()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    // listen msg from service
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(msg: CommunicateMng.MsgFromService) {
        textView.text = msg.msg
    }
}
