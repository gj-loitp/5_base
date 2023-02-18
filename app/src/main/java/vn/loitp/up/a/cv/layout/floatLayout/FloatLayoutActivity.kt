package vn.loitp.up.a.cv.layout.floatLayout

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
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import io.hamed.floatinglayout.FloatingLayout
import io.hamed.floatinglayout.callback.FloatingListener
import vn.loitp.R
import vn.loitp.databinding.ALayoutFloatBinding

@LogTag("FloatLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FloatLayoutActivity : BaseActivityFont() {
    private lateinit var binding: ALayoutFloatBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutFloatBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = FloatLayoutActivity::class.java.simpleName
        }

        binding.btnOpen.setSafeOnClickListener {
            if (isNeedPermission()) {
                showShortInformation("Need permission")
            } else {
                showFloating()
            }
        }
        binding.btnPermission.setSafeOnClickListener {
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
