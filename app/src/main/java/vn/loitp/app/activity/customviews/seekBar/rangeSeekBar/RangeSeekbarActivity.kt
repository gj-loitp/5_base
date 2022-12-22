package vn.loitp.app.activity.customviews.seekBar.rangeSeekBar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.sb.range.DoubleValueSeekBarView
import com.loitp.views.sb.range.OnDoubleValueSeekBarChangeListener
import com.loitp.views.sb.range.OnRangeSeekBarChangeListener
import com.loitp.views.sb.range.RangeSeekBarView
import kotlinx.android.synthetic.main.activity_0.*
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_range_seekbar.*
import vn.loitp.app.R

@LogTag("RangeSeekbarActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class RangeSeekbarActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_range_seekbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(view = it, runnable = {
                    LSocialUtil.openUrlInBrowser(
                        context = context,
                        url = "https://github.com/MohammedAlaaMorsi/RangeSeekBar"
                    )
                })
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = RangeSeekbarActivity::class.java.simpleName
        }

        rangeSeekbar1.currentValue = 20
        rangeSeekbar1.step = 5

        rangeSeekbar2.currentValue = 30
        rangeSeekbar2.step = 10

        rangeSeekbar3.currentValue = 40
        rangeSeekbar3.step = 15

        rangeSeekbar4.currentValue = 50
        rangeSeekbar4.step = 15

        rangeSeekbar.currentMinValue = 50
        rangeSeekbar.minStep = 10

        rangeSeekbar.currentMaxValue = 140
        rangeSeekbar.maxStep = 20

        rangeSeekbar1.setAnimated(true, 3000L)
        rangeSeekbar2.setAnimated(true, 3000L)
        rangeSeekbar3.setAnimated(true, 3000L)
        rangeSeekbar4.setAnimated(true, 3000L)

        rangeSeekbar1.setOnRangeSeekBarViewChangeListener(object : OnRangeSeekBarChangeListener {
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


        rangeSeekbar2.setOnRangeSeekBarViewChangeListener(object : OnRangeSeekBarChangeListener {
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

        rangeSeekbar3.setOnRangeSeekBarViewChangeListener(object : OnRangeSeekBarChangeListener {
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


        rangeSeekbar4.setOnRangeSeekBarViewChangeListener(object : OnRangeSeekBarChangeListener {
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

        rangeSeekbar.setOnRangeSeekBarViewChangeListener(object :
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

        btnValue.setSafeOnClickListener {
            getValues()
        }
    }

    private fun getValues() {
        showShortInformation("1->${rangeSeekbar1.currentValue}\n" + "2->${rangeSeekbar2.currentValue}\n" + "3->${rangeSeekbar3.currentValue}\n" + "4->${rangeSeekbar4.currentValue}\n" + "min->${rangeSeekbar.currentMinValue} max->${rangeSeekbar.currentMaxValue}")
    }
}
