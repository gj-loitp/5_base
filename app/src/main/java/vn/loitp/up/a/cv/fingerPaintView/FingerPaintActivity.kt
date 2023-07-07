package vn.loitp.up.a.cv.fingerPaintView

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
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AFingerPaintViewBinding

@LogTag("FingerPaintActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class FingerPaintActivity : BaseActivityFont(), SeekBar.OnSeekBarChangeListener,
    View.OnClickListener {
    private lateinit var binding: AFingerPaintViewBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFingerPaintViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
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
        binding.close.setOnClickListener(this)
        binding.save.setOnClickListener(this)
        binding.undo.setOnClickListener(this)
        binding.clear.setOnClickListener(this)
        binding.red.setOnSeekBarChangeListener(this)
        binding.green.setOnSeekBarChangeListener(this)
        binding.blue.setOnSeekBarChangeListener(this)
        binding.tolerance.setOnSeekBarChangeListener(this)
        binding.width.setOnSeekBarChangeListener(this)
        binding.normal.setOnClickListener(this)
        binding.emboss.setOnClickListener(this)
        binding.blur.setOnClickListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar?.id) {
            binding.red.id, binding.green.id, binding.blue.id -> {
                val r = binding.red.progress
                val g = binding.green.progress
                val b = binding.blue.progress
                val color = Color.argb(255, r, g, b)
                binding.finger.strokeColor = color
                binding.colorPreview.setBackgroundColor(color)
            }
            binding.tolerance.id -> {
                binding.finger.touchTolerance = progress.toFloat()
            }
            binding.width.id -> {
                binding.finger.strokeWidth = progress.toFloat()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.undo -> binding.finger.undo()
            binding.clear -> binding.finger.clear()
            binding.close -> hidePreview()
            binding.save -> showPreview()
            binding.emboss -> binding.finger.emboss()
            binding.blur -> binding.finger.blur()
            binding.normal -> binding.finger.normal()
        }
    }

    private fun showPreview() {
        binding.previewContainer.visibility = View.VISIBLE
        binding.preview.setImageDrawable(binding.finger.drawable)
    }

    private fun hidePreview() {
        binding.previewContainer.visibility = View.INVISIBLE
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onBaseBackPressed() {
        if (binding.previewContainer.visibility == View.VISIBLE) {
            hidePreview()
        } else {
            super.onBaseBackPressed()
        }

    }
}
