package vn.loitp.up.a.picker.gradientColorPickerBar

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
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.pk.gradientColorPickerBar.GradientColorPickerBar
import vn.loitp.R
import vn.loitp.databinding.APickerGradientColorPickerBarBinding

@LogTag("GradientColorPickerBarActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class GradientColorPickerBarActivity : BaseActivityFont() {

    private lateinit var binding: APickerGradientColorPickerBarBinding

    private val colorList: List<IntArray> by lazy {
        listOf(
            resources.getIntArray(R.array.colors),
            resources.getIntArray(R.array.color1),
            resources.getIntArray(R.array.color2)
        )
    }

//    override fun setLayoutResourceId(): Int {
//        return R.layout.a_picker_gradient_color_picker_bar
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APickerGradientColorPickerBarBinding.inflate(layoutInflater)
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
                            url = "https://github.com/wangpeiyuan/GradientColorPickerBar"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = GradientColorPickerBarActivity::class.java.simpleName
        }

        binding.changColor.setOnClickListener {
            binding.colorPickerBar.setColors(colorList.random())
        }
        binding.resetProgress.setOnClickListener {
            binding.colorPickerBar.setProgress(0f)
        }
        binding.changeBarStyle.setOnClickListener {
            binding.colorPickerBar.setBarStyle(10f.dp, 12f.dp)
        }
        binding.changeThumbStyle.setOnClickListener {
            binding.colorPickerBar.setThumbStyle(30f.dp, 15f.dp, Color.WHITE, 3f.dp)
        }
        binding.changeOrientation.setOnClickListener {
            binding.colorPickerBar.setOrientation(
                if (binding.colorPickerBar.getOrientation() == GradientColorPickerBar.HORIZONTAL)
                    GradientColorPickerBar.VERTICAL else GradientColorPickerBar.HORIZONTAL
            )
        }
        binding.colorPickerBar.setOnChangeListener(object :
            GradientColorPickerBar.OnChangeListener {
            override fun onProgressChanged(
                gradientColorPickBar: GradientColorPickerBar,
                progress: Float,
                color: Int,
                fromUser: Boolean
            ) {
                binding.tvColor.text = GradientColorPickerBar.parseColorInt(color)
                binding.tvColor.setTextColor(color)
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
