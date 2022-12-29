package vn.loitp.a.func.activityAndService

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
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
            CommunicateMng.postFromActivity(
                CommunicateMng.MsgFromActivity("${bt0.text} +${System.currentTimeMillis()}")
            )
        }

        btTestStartActivityForResult.setSafeOnClickListener {
            launchActivityTest()
        }
    }

    private fun handleNotify() {
        if (Settings.canDrawOverlays(this)) {
            showShortInformation("onClick TestService")
            textView.text = ""
            startService(Intent(this, TestService::class.java))
        } else {
            launchActivityForResult(
                intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                ),
                withAnim = true,
                data = { intent ->
                    resultOverlay.launch(intent)
                })
        }
    }

    // listen msg from service
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(msg: CommunicateMng.MsgFromService) {
        textView.text = msg.msg
    }

    private fun launchActivityTest() {
        launchActivityForResult(
            cls = EmptyActivity::class.java,
            withAnim = true,
            data = { intent ->
                intent.putExtra(KEY_INPUT, "Hello Loitp ${System.currentTimeMillis()}")
                resultTest.launch(intent)
            })
    }

    private val resultTest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    val output = intent.getStringExtra(KEY_OUTPUT)
                    showShortInformation(output)
                }
            }
        }

    private val resultOverlay =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleNotify()
        }
}
