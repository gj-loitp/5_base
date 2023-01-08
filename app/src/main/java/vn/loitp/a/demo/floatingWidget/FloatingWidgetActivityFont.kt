package vn.loitp.a.demo.floatingWidget

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
import kotlinx.android.synthetic.main.a_demo_floating_widget.*
import kotlinx.android.synthetic.main.a_demo_floating_widget.btNotifyMe
import kotlinx.android.synthetic.main.a_demo_floating_widget.lActionBar
import kotlinx.android.synthetic.main.a_func_service_communicate.*
import vn.loitp.R

@LogTag("FloatingWidgetActivity")
@IsFullScreen(false)
class FloatingWidgetActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_demo_floating_widget
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = FloatingWidgetActivityFont::class.java.simpleName
        }
        btNotifyMe.setSafeOnClickListener {
            startService()
        }
    }

    private fun startService() {
        if (Settings.canDrawOverlays(this)) {
            startService(Intent(this, FloatingViewService::class.java))
            onBaseBackPressed()
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

    private val resultOverlay =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            startService()
        }
}
