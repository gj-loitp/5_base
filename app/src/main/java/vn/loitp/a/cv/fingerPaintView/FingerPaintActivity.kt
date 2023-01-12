package vn.loitp.a.cv.fingerPaintView

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_finger_paint_view.*
import vn.loitp.R

@LogTag("FingerPaintActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FingerPaintActivity : BaseActivityFont(), SeekBar.OnSeekBarChangeListener,
    View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_finger_paint_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
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
