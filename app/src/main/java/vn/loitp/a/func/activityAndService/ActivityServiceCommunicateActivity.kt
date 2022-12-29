package vn.loitp.a.func.activityAndService

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResult
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_func_service_communicate.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.loitp.R
import vn.loitp.a.demo.floatingWidget.CommunicateMng
import vn.loitp.app.EmptyActivity

@LogTag("ActivityServiceCommunicateActivity")
@IsFullScreen(false)
class ActivityServiceCommunicateActivity : BaseFontActivity() {

    companion object {
        private const val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084

        const val KEY_INPUT = "KEY_INPUT"
        const val KEY_OUTPUT = "KEY_OUTPUT"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.a_func_service_communicate
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
            this.tvTitle?.text = ActivityServiceCommunicateActivity::class.java.simpleName
        }

        btNotifyMe.setSafeOnClickListener {
            handleNotify()
        }
        bt0.setSafeOnClickListener {
            CommunicateMng.postFromActivity(CommunicateMng.MsgFromActivity(bt0.text.toString()))
        }

        btTestStartActivityForResult.setSafeOnClickListener {
            launchActivityTest()
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
        //TODO startActivityForResult
        startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)
        LActivityUtil.tranIn(this)
    }

    //TODO onActivityResult
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        when (requestCode) {
            CODE_DRAW_OVER_OTHER_APP_PERMISSION -> {
                handleNotify()
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    // listen msg from service
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(msg: CommunicateMng.MsgFromService) {
        textView.text = msg.msg
    }

    private fun launchActivityTest() {
        launchActivity(
            cls = EmptyActivity::class.java,
            launchForResult = true,
            withAnim = true,
            data = { intent ->
                intent.putExtra(KEY_INPUT, "Hello Loitp ${System.currentTimeMillis()}")
            })
    }
    
    override fun getResultActivity(result: ActivityResult) {
        super.getResultActivity(result)
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                val output = intent.getStringExtra(KEY_OUTPUT)
                showShortInformation(output)
            }
        }
    }
}
