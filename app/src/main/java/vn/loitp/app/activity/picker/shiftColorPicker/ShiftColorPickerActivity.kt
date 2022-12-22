package vn.loitp.app.activity.picker.shiftColorPicker

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.picker.shiftColorPicker.OnColorChangedListener
import kotlinx.android.synthetic.main.activity_0.*
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_shift_color_picker.*
import vn.loitp.R

@LogTag("ShiftColorPickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ShiftColorPickerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shift_color_picker
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
                            url = "https://github.com/DASAR-zz/ShiftColorPicker"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ShiftColorPickerActivity::class.java.simpleName
        }

        colorPicker.setOnColorChangedListener(object : OnColorChangedListener {
            override fun onColorChanged(c: Int) {
                showShortInformation("Selected color " + Integer.toHexString(c))
            }
        })

        btGetSelectedColor.setSafeOnClickListener {
            showShortInformation(
                msg = "Selected color: ${colorPicker.color} -> ${
                    Integer.toHexString(
                        colorPicker.color
                    )
                }"
            )
        }

        colorPicker2.apply {
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

        btGetSelectedColor2.setSafeOnClickListener {
            showShortInformation(
                msg = "Selected color: ${colorPicker2.color} -> ${
                    Integer.toHexString(
                        colorPicker2.color
                    )
                }"
            )
        }
    }
}

