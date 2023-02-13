package vn.loitp.up.a.demo.floatingVideo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ADemoFloatingVideoBinding

@LogTag("FloatingWidgetVideoActivity")
@IsFullScreen(false)
class FloatingWidgetVideoActivity : BaseActivityFont() {

    private lateinit var binding: ADemoFloatingVideoBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADemoFloatingVideoBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = FloatingWidgetVideoActivity::class.java.simpleName
        }
        binding.btShow.setSafeOnClickListener {
            handleShow()
        }
        binding.btEdge.setSafeOnClickListener {
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
