package vn.loitp.app.activity.function.activityandservice

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_func_service_communicate.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.loitp.app.R
import vn.loitp.app.activity.demo.floatingwidget.CommunicateMng

@LayoutId(R.layout.activity_func_service_communicate)
@LogTag("ActivityServiceComunicateActivity")
@IsFullScreen(false)
class ActivityServiceComunicateActivity : BaseFontActivity() {

    companion object {
        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btNotifyMe.setOnClickListener {
            handleNotify()
        }
        bt0.setOnClickListener {
            CommunicateMng.postFromActivity(CommunicateMng.MsgFromActivity(bt0.text.toString()))
        }
    }

    private fun handleNotify() {
        //Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
        } else {
            showShort("onClick TestService")
            textView.text = ""
            startService(Intent(this, TestService::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            handleNotify()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    //listen msg from service
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(msg: CommunicateMng.MsgFromService) {
        textView.text = msg.msg
    }
}
