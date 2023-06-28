package vn.loitp.up.a.func.floatingToast

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.FONT_PATH
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import hari.floatingtoast.FloatingToast
import vn.loitp.R
import vn.loitp.databinding.AFloatingToastBinding


@LogTag("FloatingToastActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FloatingToastActivity : BaseActivityFont() {

    private lateinit var binding: AFloatingToastBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFloatingToastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/hariprasanths/FloatingToast"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FloatingToastActivity::class.java.simpleName
        }

        binding.progressBar.setOnClickListener {
            FloatingToast.makeToast(
                /* activity = */ this,
                /* text = */ "progressBar Hello ${System.currentTimeMillis()}",
                /* duration = */ FloatingToast.LENGTH_MEDIUM
            ).show()
        }
        binding.iv.setOnClickListener {
            FloatingToast.makeToast(
                /* activity = */ this,
                /* text = */ "iv Hello ${System.currentTimeMillis()}",
                /* duration = */ FloatingToast.LENGTH_MEDIUM
            ).showAtTouchPosition(binding.progressBar)
        }
        binding.tv.setOnClickListener {
            showToast()
        }
    }

    private fun showToast() {
        val customFont = Typeface.createFromAsset(assets, FONT_PATH)
        FloatingToast.makeToast(
            /* activity = */ this,
            /* text = */ "Customize floating toast ${System.currentTimeMillis()}",
            /* duration = */ FloatingToast.LENGTH_LONG
        )
            .setGravity(FloatingToast.GRAVITY_MID_TOP)
            .setFadeOutDuration(FloatingToast.FADE_DURATION_LONG)
            .setTextSizeInDp(32f)
            .setBackgroundBlur(true)
            .setFloatDistance(FloatingToast.DISTANCE_SHORT.toFloat())
            .setTextColor(Color.parseColor("#ffffff"))
            .setShadowLayer(5f, 1f, 1f, Color.parseColor("#000000"))
            .setTextTypeface(customFont)
            .show()    //Show toast at the specified fixed position
    }
}
