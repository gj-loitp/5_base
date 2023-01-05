package vn.loitp.a.cv.cornerSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.Toolbar
import com.github.heyalex.cornersheet.behavior.CornerSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.CornerMaterialSheetBehavior
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import kotlinx.android.synthetic.main.a_main_cs.*
import vn.loitp.R
import java.math.RoundingMode

@LogTag("BehaviorSampleActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class BehaviorSampleActivityFont : BaseActivityFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_main_cs
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cornerDrawer = findViewById<View>(R.id.corner_behavior_container)
        val behavior = BottomSheetBehavior.from(cornerDrawer) as CornerMaterialSheetBehavior

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                expandedValue.text = "Expanded value: ${
                    slideOffset.toBigDecimal().setScale(
                        /* newScale = */ 2,
                        /* roundingMode = */ RoundingMode.UP
                    ).toDouble()
                }"
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

        })

        behavior.halfExpandedRatio = 0.3f

        findViewById<Toolbar>(R.id.toolbar).setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            behavior_state.text = "HALF EXPANDED"
        }

        expand.setOnClickListener {
            behavior.horizontalState = CornerSheetBehavior.STATE_EXPANDED
            behavior_state.text = "EXPANDED"
        }

        collapsed.setOnClickListener {
            behavior.horizontalState = CornerSheetBehavior.STATE_COLLAPSED
            behavior_state.text = "COLLAPSED"
        }

        hidden.setOnClickListener {
            behavior.horizontalState = CornerSheetBehavior.STATE_HIDDEN
            behavior_state.text = "HIDDEN"
        }

        val seekbar = findViewById<AppCompatSeekBar>(R.id.seek_peek_height)
        seekbar.max = 800
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                behavior.setHorizontalPeekHeight(progress, true)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }
}
