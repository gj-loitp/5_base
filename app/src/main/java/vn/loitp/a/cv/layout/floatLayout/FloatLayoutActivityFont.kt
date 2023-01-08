package vn.loitp.a.cv.layout.floatLayout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import io.hamed.floatinglayout.FloatingLayout
import io.hamed.floatinglayout.callback.FloatingListener
import kotlinx.android.synthetic.main.a_layout_float.*
import kotlinx.android.synthetic.main.l_float_layout.*
import vn.loitp.R

@LogTag("FloatLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FloatLayoutActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_float
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
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/HamedTaherpour/floating-layout-android"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FloatLayoutActivityFont::class.java.simpleName
        }

        btnOpen.setSafeOnClickListener {
            if (isNeedPermission()) {
                showShortInformation("Need permission")
            } else {
                showFloating()
            }
        }
        btnPermission.setSafeOnClickListener {
            if (isNeedPermission()) {
                requestPermission()
            }
        }
    }

    private var floatingLayout: FloatingLayout? = null

    private fun isNeedPermission(): Boolean {
        return !Settings.canDrawOverlays(this)
    }

    private fun requestPermission() {
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

    private fun showFloating() {
        floatingLayout = FloatingLayout(
            /* context = */ this,
            /* layoutRes = */ R.layout.l_float_layout
        )
        floatingLayout?.let {
            it.setFloatingListener(object : FloatingListener {
                override fun onCreateListener(view: View?) {
                    val btnCloseFloat = view?.findViewById<Button>(R.id.btnCloseFloat)
                    btnCloseFloat?.setOnClickListener {
                        floatingLayout?.destroy()
                    }
                }

                override fun onCloseListener() {
                    showShortInformation("Close")
                }

            })
            it.create()
        }
    }

    private val resultOverlay =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            showFloating()
        }
}
