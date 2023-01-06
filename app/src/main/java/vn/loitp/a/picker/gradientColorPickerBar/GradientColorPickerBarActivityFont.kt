package vn.loitp.a.picker.gradientColorPickerBar

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.pk.gradientColorPickerBar.GradientColorPickerBar
import kotlinx.android.synthetic.main.a_picker_gradient_color_picker_bar.*
import vn.loitp.R

@LogTag("GradientColorPickerBarActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class GradientColorPickerBarActivityFont : BaseActivityFont() {

    private val colorList: List<IntArray> by lazy {
        listOf(
            resources.getIntArray(R.array.colors),
            resources.getIntArray(R.array.color1),
            resources.getIntArray(R.array.color2)
        )
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.a_picker_gradient_color_picker_bar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.apply {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = this,
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/wangpeiyuan/GradientColorPickerBar"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = GradientColorPickerBarActivityFont::class.java.simpleName
        }

        changColor.setOnClickListener {
            colorPickerBar.setColors(colorList.random())
        }
        resetProgress.setOnClickListener {
            colorPickerBar.setProgress(0f)
        }
        changeBarStyle.setOnClickListener {
            colorPickerBar.setBarStyle(10f.dp, 12f.dp)
        }
        changeThumbStyle.setOnClickListener {
            colorPickerBar.setThumbStyle(30f.dp, 15f.dp, Color.WHITE, 3f.dp)
        }
        changeOrientation.setOnClickListener {
            colorPickerBar.setOrientation(
                if (colorPickerBar.getOrientation() == GradientColorPickerBar.HORIZONTAL)
                    GradientColorPickerBar.VERTICAL else GradientColorPickerBar.HORIZONTAL
            )
        }
        colorPickerBar.setOnChangeListener(object : GradientColorPickerBar.OnChangeListener {
            override fun onProgressChanged(
                gradientColorPickBar: GradientColorPickerBar,
                progress: Float,
                color: Int,
                fromUser: Boolean
            ) {
                tvColor.text = GradientColorPickerBar.parseColorInt(color)
                tvColor.setTextColor(color)
            }
        })
    }
}

private val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )