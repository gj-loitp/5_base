package vn.loitp.up.a.cv.cornerSheet

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
import com.loitp.core.common.NOT_FOUND
import vn.loitp.R
import vn.loitp.databinding.AMainCsBinding
import java.math.RoundingMode

@LogTag("BehaviorSampleActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class BehaviorSampleActivity : BaseActivityFont() {
    private lateinit var binding: AMainCsBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMainCsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cornerDrawer = findViewById<View>(R.id.corner_behavior_container)
        val behavior = BottomSheetBehavior.from(cornerDrawer) as CornerMaterialSheetBehavior

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.expandedValue.text = "Expanded value: ${
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
            binding.behaviorState.text = "HALF EXPANDED"
        }

        binding.expand.setOnClickListener {
            behavior.horizontalState = CornerSheetBehavior.STATE_EXPANDED
            binding.behaviorState.text = "EXPANDED"
        }

        binding.collapsed.setOnClickListener {
            behavior.horizontalState = CornerSheetBehavior.STATE_COLLAPSED
            binding.behaviorState.text = "COLLAPSED"
        }

        binding.hidden.setOnClickListener {
            behavior.horizontalState = CornerSheetBehavior.STATE_HIDDEN
            binding.behaviorState.text = "HIDDEN"
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
