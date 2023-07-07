package vn.loitp.up.a.cv.sb.range

import android.annotation.SuppressLint
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
import com.loitp.views.sb.range.DoubleValueSeekBarView
import com.loitp.views.sb.range.OnDoubleValueSeekBarChangeListener
import com.loitp.views.sb.range.OnRangeSeekBarChangeListener
import com.loitp.views.sb.range.RangeSeekBarView
import vn.loitp.R
import vn.loitp.databinding.ASbRangeBinding

@LogTag("RangeSeekbarActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class RangeSeekbarActivity : BaseActivityFont() {
    private lateinit var binding: ASbRangeBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASbRangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/MohammedAlaaMorsi/RangeSeekBar"
                    )
                })
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = RangeSeekbarActivity::class.java.simpleName
        }

        binding.rangeSeekbar1.currentValue = 20
        binding.rangeSeekbar1.step = 5

        binding.rangeSeekbar2.currentValue = 30
        binding.rangeSeekbar2.step = 10

        binding.rangeSeekbar3.currentValue = 40
        binding.rangeSeekbar3.step = 15

        binding.rangeSeekbar4.currentValue = 50
        binding.rangeSeekbar4.step = 15

        binding.rangeSeekbar.currentMinValue = 50
        binding.rangeSeekbar.minStep = 10

        binding.rangeSeekbar.currentMaxValue = 140
        binding.rangeSeekbar.maxStep = 20

        binding.rangeSeekbar1.setAnimated(true, 3000L)
        binding.rangeSeekbar2.setAnimated(true, 3000L)
        binding.rangeSeekbar3.setAnimated(true, 3000L)
        binding.rangeSeekbar4.setAnimated(true, 3000L)

        binding.rangeSeekbar1.setOnRangeSeekBarViewChangeListener(object :
            OnRangeSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: RangeSeekBarView?, progress: Int, fromUser: Boolean
            ) {
                logD("onChanged:1-> $progress")
            }

            override fun onStartTrackingTouch(seekBar: RangeSeekBarView?, progress: Int) {
                logD("onStart:1-> $progress")

            }

            override fun onStopTrackingTouch(seekBar: RangeSeekBarView?, progress: Int) {
                logD("onStop:1-> $progress")
            }
        })


        binding.rangeSeekbar2.setOnRangeSeekBarViewChangeListener(object :
            OnRangeSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: RangeSeekBarView?, progress: Int, fromUser: Boolean
            ) {
                logD("onChanged:2-> $progress")
            }

            override fun onStartTrackingTouch(seekBar: RangeSeekBarView?, progress: Int) {
                logD("onStart:2-> $progress")

            }

            override fun onStopTrackingTouch(seekBar: RangeSeekBarView?, progress: Int) {
                logD("onStop:2-> $progress")

            }

        })

        binding.rangeSeekbar3.setOnRangeSeekBarViewChangeListener(object :
            OnRangeSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: RangeSeekBarView?, progress: Int, fromUser: Boolean
            ) {
                logD("onChanged:3-> $progress")
            }

            override fun onStartTrackingTouch(seekBar: RangeSeekBarView?, progress: Int) {
                logD("onStart:3-> $progress")

            }

            override fun onStopTrackingTouch(seekBar: RangeSeekBarView?, progress: Int) {
                logD("onStop:3-> $progress")

            }

        })


        binding.rangeSeekbar4.setOnRangeSeekBarViewChangeListener(object :
            OnRangeSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: RangeSeekBarView?, progress: Int, fromUser: Boolean
            ) {
                logD("onChanged:4-> $progress")
            }

            override fun onStartTrackingTouch(seekBar: RangeSeekBarView?, progress: Int) {
                logD("onStart:4-> $progress")

            }

            override fun onStopTrackingTouch(seekBar: RangeSeekBarView?, progress: Int) {
                logD("onStop:4-> $progress")
            }

        })

        binding.rangeSeekbar.setOnRangeSeekBarViewChangeListener(object :
            OnDoubleValueSeekBarChangeListener {
            override fun onValueChanged(
                seekBar: DoubleValueSeekBarView?, min: Int, max: Int, fromUser: Boolean
            ) {
                logD("onChanged-> Min $min : Max $max")

            }

            override fun onStartTrackingTouch(
                seekBar: DoubleValueSeekBarView?, min: Int, max: Int
            ) {
                logD("onStart-> Min $min : Max $max")
            }

            override fun onStopTrackingTouch(seekBar: DoubleValueSeekBarView?, min: Int, max: Int) {
                logD("onStop Min $min : Max $max")
            }

        })

        binding.btnValue.setSafeOnClickListener {
            getValues()
        }
    }

    private fun getValues() {
        showShortInformation("1->${binding.rangeSeekbar1.currentValue}\n" + "2->${binding.rangeSeekbar2.currentValue}\n" + "3->${binding.rangeSeekbar3.currentValue}\n" + "4->${binding.rangeSeekbar4.currentValue}\n" + "min->${binding.rangeSeekbar.currentMinValue} max->${binding.rangeSeekbar.currentMaxValue}")
    }
}
