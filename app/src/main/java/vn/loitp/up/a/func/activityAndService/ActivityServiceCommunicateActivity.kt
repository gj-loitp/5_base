package vn.loitp.up.a.func.activityAndService

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.loitp.R
import vn.loitp.a.demo.floatingWidget.CommunicateMng
import vn.loitp.databinding.AFuncServiceCommunicateBinding
import vn.loitp.up.app.EmptyActivity

@LogTag("ActivityServiceCommunicateActivity")
@IsFullScreen(false)
class ActivityServiceCommunicateActivity : BaseActivityFont() {

    companion object {
        const val KEY_INPUT = "KEY_INPUT"
        const val KEY_OUTPUT = "KEY_OUTPUT"
    }

    private lateinit var binding: AFuncServiceCommunicateBinding

    override fun setLayoutResourceId(): Int {
        return R.layout.a_func_service_communicate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFuncServiceCommunicateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ActivityServiceCommunicateActivity::class.java.simpleName
        }

        binding.btNotifyMe.setSafeOnClickListener {
            handleNotify()
        }
        binding.bt0.setSafeOnClickListener {
            CommunicateMng.postFromActivity(
                CommunicateMng.MsgFromActivity("${binding.bt0.text} +${System.currentTimeMillis()}")
            )
        }

        binding.btTestStartActivity4Result.setSafeOnClickListener {
            launchActivityTest()
        }
    }

    private fun handleNotify() {
        if (Settings.canDrawOverlays(this)) {
            showShortInformation("onClick TestService")
            binding.textView.text = ""
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
        binding.textView.text = msg.msg
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
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            handleNotify()
        }
}
