package vn.loitp.up.a.cv.sb.materialrangebar

import android.os.Bundle
import androidx.core.view.isVisible
import com.appyvet.materialrangebar.RangeBar
import com.appyvet.materialrangebar.RangeBar.OnRangeBarChangeListener
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AMaterialRangeBarBinding


@LogTag("MaterialRangeBarActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MaterialRangeBarActivity : BaseActivityFont() {

    private lateinit var binding: AMaterialRangeBarBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMaterialRangeBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/oli107/material-range-bar"
                    )
                })
                isVisible = true
                setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = MaterialRangeBarActivity::class.java.simpleName
        }

        binding.rangebar1.setOnRangeBarChangeListener(object : OnRangeBarChangeListener {
            override fun onRangeChangeListener(
                rangeBar: RangeBar,
                leftPinIndex: Int,
                rightPinIndex: Int,
                leftPinValue: String,
                rightPinValue: String
            ) {
                logD("onRangeChangeListener $leftPinIndex $rightPinIndex $leftPinValue $rightPinValue")
            }

            override fun onTouchEnded(rangeBar: RangeBar) {}
            override fun onTouchStarted(rangeBar: RangeBar) {}
        })

        binding.rangebar1.setFormatter { // Transform the String s here then return s
            "i: $it"
        }
    }
}
