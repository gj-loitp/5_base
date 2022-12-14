package vn.loitp.app.activity.customviews.fingerPaintView

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_finger_paint_view.*
import vn.loitp.app.R

@LogTag("FingerPaintActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FingerPaintActivity : BaseFontActivity(), SeekBar.OnSeekBarChangeListener,
    View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_finger_paint_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/PicnicSupermarket/FingerPaintView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = FingerPaintActivity::class.java.simpleName
        }
        close.setOnClickListener(this)
        save.setOnClickListener(this)
        undo.setOnClickListener(this)
        clear.setOnClickListener(this)
        red.setOnSeekBarChangeListener(this)
        green.setOnSeekBarChangeListener(this)
        blue.setOnSeekBarChangeListener(this)
        tolerance.setOnSeekBarChangeListener(this)
        width.setOnSeekBarChangeListener(this)
        normal.setOnClickListener(this)
        emboss.setOnClickListener(this)
        blur.setOnClickListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar?.id) {
            red.id, green.id, blue.id -> {
                val r = red.progress
                val g = green.progress
                val b = blue.progress
                val color = Color.argb(255, r, g, b)
                finger.strokeColor = color
                colorPreview.setBackgroundColor(color)
            }
            tolerance.id -> {
                finger.touchTolerance = progress.toFloat()
            }
            width.id -> {
                finger.strokeWidth = progress.toFloat()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            undo -> finger.undo()
            clear -> finger.clear()
            close -> hidePreview()
            save -> showPreview()
            emboss -> finger.emboss()
            blur -> finger.blur()
            normal -> finger.normal()
        }
    }

    private fun showPreview() {
        previewContainer.visibility = View.VISIBLE
        preview.setImageDrawable(finger.drawable)
    }

    private fun hidePreview() {
        previewContainer.visibility = View.INVISIBLE
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onBaseBackPressed() {
        if (previewContainer.visibility == View.VISIBLE) {
            hidePreview()
        } else {
            super.onBaseBackPressed()
        }

    }
}
