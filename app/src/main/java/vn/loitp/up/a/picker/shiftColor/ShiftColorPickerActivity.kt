package vn.loitp.up.a.picker.shiftColor

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.picker.shiftColor.OnColorChangedListener
import vn.loitp.R
import vn.loitp.databinding.APickerShiftColorBinding

@LogTag("ShiftColorPickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ShiftColorPickerActivity : BaseActivityFont() {

    private lateinit var binding: APickerShiftColorBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APickerShiftColorBinding.inflate(layoutInflater)
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
                            url = "https://github.com/DASAR-zz/ShiftColorPicker"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ShiftColorPickerActivity::class.java.simpleName
        }

        binding.colorPicker.setOnColorChangedListener(object : OnColorChangedListener {
            override fun onColorChanged(c: Int) {
                showShortInformation("Selected color " + Integer.toHexString(c))
            }
        })

        binding.btGetSelectedColor.setSafeOnClickListener {
            showShortInformation(
                msg = "Selected color: ${binding.colorPicker.color} -> ${
                    Integer.toHexString(
                        binding.colorPicker.color
                    )
                }"
            )
        }

        binding.colorPicker2.apply {
            colors = intArrayOf(
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.CYAN,
                Color.MAGENTA,
            )
            setSelectedColor(Color.RED)
            setOnColorChangedListener(object : OnColorChangedListener {
                override fun onColorChanged(c: Int) {
                    showShortInformation("Selected color " + Integer.toHexString(c))
                }
            })
        }

        binding.btGetSelectedColor2.setSafeOnClickListener {
            showShortInformation(
                msg = "Selected color: ${binding.colorPicker2.color} -> ${
                    Integer.toHexString(
                        binding.colorPicker2.color
                    )
                }"
            )
        }
    }
}

