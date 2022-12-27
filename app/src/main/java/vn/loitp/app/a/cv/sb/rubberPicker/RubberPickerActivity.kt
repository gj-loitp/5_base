package vn.loitp.app.a.cv.sb.rubberPicker

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
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_sb_rubber_picker.*
import vn.loitp.R

@LogTag("RubberPickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class RubberPickerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sb_rubber_picker
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
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/Chrisvin/RubberPicker"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = RubberPickerActivity::class.java.simpleName
        }

        elasticBehavior.setOnCheckedChangeListener { _, checkedId ->
            when (findViewById<RadioButton>(checkedId).text) {
                "Cubic" -> {
                    rubberSeekBar.setElasticBehavior(ElasticBehavior.CUBIC)
                    rubberRangePicker.setElasticBehavior(ElasticBehavior.CUBIC)
                }
                "Linear" -> {
                    rubberSeekBar.setElasticBehavior(ElasticBehavior.LINEAR)
                    rubberRangePicker.setElasticBehavior(ElasticBehavior.LINEAR)
                }
                "Rigid" -> {
                    rubberSeekBar.setElasticBehavior(ElasticBehavior.RIGID)
                    rubberRangePicker.setElasticBehavior(ElasticBehavior.RIGID)
                }
            }
        }

        stretchRange.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                stretchRangeValue.text = progress.toString()
                rubberSeekBar.setStretchRange(progress.toFloat())
                rubberRangePicker.setStretchRange(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        dampingRatio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                dampingRatioValue.text = (progress.toFloat() / 10).toString()
                rubberSeekBar.setDampingRatio(progress.toFloat() / 10)
                rubberRangePicker.setDampingRatio(progress.toFloat() / 10)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        stiffness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val progressValue = if (progress != 0) progress * 50 else 1
                stiffnessValue.text = (progressValue).toString()
                rubberSeekBar.setStiffness(progressValue.toFloat())
                rubberRangePicker.setStiffness(progressValue.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        defaultThumbRadius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val progressValue = if (progress == 0) 1 else progress
                defaultThumbRadiusValue.text = progressValue.toString()
                rubberSeekBar.setThumbRadius(progressValue.toFloat())
                rubberRangePicker.setThumbRadius(progressValue.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        normalTrackWidth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                normalTrackWidthValue.text = progress.toString()
                rubberSeekBar.setNormalTrackWidth(progress.toFloat())
                rubberRangePicker.setNormalTrackWidth(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        highlightTrackWidth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                highlightTrackWidthValue.text = progress.toString()
                rubberSeekBar.setHighlightTrackWidth(progress.toFloat())
                rubberRangePicker.setHighlightTrackWidth(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        rubberSeekBar.setOnRubberSeekBarChangeListener(object :
            RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                rubberSeekBarValue.text = value.toString()
            }

            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })

        rubberRangePicker.setOnRubberRangePickerChangeListener(object :
            RubberRangePicker.OnRubberRangePickerChangeListener {
            override fun onProgressChanged(
                rangePicker: RubberRangePicker,
                startValue: Int,
                endValue: Int,
                fromUser: Boolean
            ) {
                rubberRangePickerStartValue.text = startValue.toString()
                rubberRangePickerEndValue.text = endValue.toString()
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
