package vn.loitp.app.activity.picker.shiftColorPicker

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.picker.shiftColorPicker.OnColorChangedListener
import kotlinx.android.synthetic.main.activity_0.*
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_shift_color_picker.*
import vn.loitp.app.R


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
            this.viewShadow?.isVisible = true
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

        colorPicker2.colors = intArrayOf(
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
        )
        colorPicker2.setSelectedColor(Color.RED)

        colorPicker2.setOnColorChangedListener(object : OnColorChangedListener {
            override fun onColorChanged(c: Int) {
                showShortInformation("Selected color " + Integer.toHexString(c))
            }
        })

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

