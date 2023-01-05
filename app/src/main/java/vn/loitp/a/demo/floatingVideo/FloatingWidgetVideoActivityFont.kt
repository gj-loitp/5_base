package vn.loitp.a.demo.floatingVideo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_demo_floating_video.*
import kotlinx.android.synthetic.main.a_demo_floating_video.lActionBar
import kotlinx.android.synthetic.main.a_func_service_communicate.*
import vn.loitp.R

@LogTag("FloatingWidgetVideoActivity")
@IsFullScreen(false)
class FloatingWidgetVideoActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_demo_floating_video
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
            this.tvTitle?.text = FloatingWidgetVideoActivityFont::class.java.simpleName
        }
        btShow.setSafeOnClickListener {
            handleShow()
        }
        btEdge.setSafeOnClickListener {
            handleEdge()
        }
    }

    private fun handleShow() {
        if (Settings.canDrawOverlays(this)) {
            startService(Intent(this, FloatingViewVideoService::class.java))
            onBaseBackPressed()
        } else {
            launchActivityForResult(
                intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                ),
                withAnim = true,
                data = { intent ->
                    resultOverlayShow.launch(intent)
                })
        }
    }

    private val resultOverlayShow =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            handleShow()
        }

    private fun handleEdge() {
        if (Settings.canDrawOverlays(this)) {
            startService(Intent(this, FloatingViewEdgeService::class.java))
            onBaseBackPressed()
        } else {
            launchActivityForResult(
                intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                ),
                withAnim = true,
                data = { intent ->
                    resultOverlayEdge.launch(intent)
                })
        }
    }

    private val resultOverlayEdge =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            handleEdge()
        }

}
