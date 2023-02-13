package vn.loitp.up.a.cv.sb.rubberPicker

import android.os.Bundle
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.core.view.isVisible
import com.jem.rubberpicker.ElasticBehavior
import com.jem.rubberpicker.RubberRangePicker
import com.jem.rubberpicker.RubberSeekBar
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ASbRubberPickerBinding

@LogTag("RubberPickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class RubberPickerActivity : BaseActivityFont() {
    private lateinit var binding: ASbRubberPickerBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASbRubberPickerBinding.inflate(layoutInflater)
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
                            url = "https://github.com/Chrisvin/RubberPicker"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = RubberPickerActivity::class.java.simpleName
        }

        binding.elasticBehavior.setOnCheckedChangeListener { _, checkedId ->
            when (findViewById<RadioButton>(checkedId).text) {
                "Cubic" -> {
                    binding.rubberSeekBar.setElasticBehavior(ElasticBehavior.CUBIC)
                    binding.rubberRangePicker.setElasticBehavior(ElasticBehavior.CUBIC)
                }
                "Linear" -> {
                    binding.rubberSeekBar.setElasticBehavior(ElasticBehavior.LINEAR)
                    binding.rubberRangePicker.setElasticBehavior(ElasticBehavior.LINEAR)
                }
                "Rigid" -> {
                    binding.rubberSeekBar.setElasticBehavior(ElasticBehavior.RIGID)
                    binding.rubberRangePicker.setElasticBehavior(ElasticBehavior.RIGID)
                }
            }
        }

        binding.stretchRange.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.stretchRangeValue.text = progress.toString()
                binding.rubberSeekBar.setStretchRange(progress.toFloat())
                binding.rubberRangePicker.setStretchRange(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.dampingRatio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.dampingRatioValue.text = (progress.toFloat() / 10).toString()
                binding.rubberSeekBar.setDampingRatio(progress.toFloat() / 10)
                binding.rubberRangePicker.setDampingRatio(progress.toFloat() / 10)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.stiffness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val progressValue = if (progress != 0) progress * 50 else 1
                binding.stiffnessValue.text = (progressValue).toString()
                binding.rubberSeekBar.setStiffness(progressValue.toFloat())
                binding.rubberRangePicker.setStiffness(progressValue.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.defaultThumbRadius.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val progressValue = if (progress == 0) 1 else progress
                binding.defaultThumbRadiusValue.text = progressValue.toString()
                binding.rubberSeekBar.setThumbRadius(progressValue.toFloat())
                binding.rubberRangePicker.setThumbRadius(progressValue.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.normalTrackWidth.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.normalTrackWidthValue.text = progress.toString()
                binding.rubberSeekBar.setNormalTrackWidth(progress.toFloat())
                binding.rubberRangePicker.setNormalTrackWidth(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.highlightTrackWidth.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.highlightTrackWidthValue.text = progress.toString()
                binding.rubberSeekBar.setHighlightTrackWidth(progress.toFloat())
                binding.rubberRangePicker.setHighlightTrackWidth(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.rubberSeekBar.setOnRubberSeekBarChangeListener(object :
            RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                binding.rubberSeekBarValue.text = value.toString()
            }

            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })

        binding.rubberRangePicker.setOnRubberRangePickerChangeListener(object :
            RubberRangePicker.OnRubberRangePickerChangeListener {
            override fun onProgressChanged(
                rangePicker: RubberRangePicker,
                startValue: Int,
                endValue: Int,
                fromUser: Boolean
            ) {
                binding.rubberRangePickerStartValue.text = startValue.toString()
                binding.rubberRangePickerEndValue.text = endValue.toString()
            }

            override fun onStartTrackingTouch(
                rangePicker: RubberRangePicker,
                isStartThumb: Boolean
            ) {
            }

            override fun onStopTrackingTouch(
                rangePicker: RubberRangePicker,
                isStartThumb: Boolean
            ) {
            }
        })

    }
}
