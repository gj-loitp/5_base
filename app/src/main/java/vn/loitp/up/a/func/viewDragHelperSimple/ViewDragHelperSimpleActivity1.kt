package vn.loitp.up.a.func.viewDragHelperSimple

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.databinding.AFuncViewDragHelperSimple1Binding

@LogTag("ViewDragHelperSimpleActivity1")
@IsFullScreen(false)
class ViewDragHelperSimpleActivity1 : BaseActivityFont(), VDHView.Callback {

    private lateinit var binding: AFuncViewDragHelperSimple1Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFuncViewDragHelperSimple1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.vdhv.setCallback(this)

        binding.btToast.setSafeOnClickListener {
            showShortInformation("Click")
        }
        binding.btMaximize.setSafeOnClickListener {
            binding.vdhv.maximize()
        }
        binding.btMinimizeBottomLeft.setSafeOnClickListener {
            binding.vdhv.minimizeBottomLeft()
        }
        binding.btMinimizeBottomRight.setSafeOnClickListener {
            binding.vdhv.minimizeBottomRight()
        }
        binding.btMinimizeTopRight.setSafeOnClickListener {
            binding.vdhv.minimizeTopRight()
        }
        binding.btMinimizeTopLeft.setSafeOnClickListener {
            binding.vdhv.minimizeTopLeft()
        }
        binding.btAlpha.setSafeOnClickListener {
            binding.vdhv.isEnableAlpha = !binding.vdhv.isEnableAlpha
        }
        binding.btShowHideHeader.setSafeOnClickListener {
            binding.vdhv.toggleShowHideHeaderView()
        }
        binding.btShowHideBody.setSafeOnClickListener {
            binding.vdhv.toggleShowHideBodyView()
        }
        binding.btSlideToPosition.setSafeOnClickListener {
            binding.vdhv.smoothSlideTo(positionX = 300, positionY = 600)
        }
        binding.btRevertMax.setSafeOnClickListener {
            if (binding.vdhv.isEnableRevertMaxSize) {
                binding.vdhv.isEnableRevertMaxSize = false
                binding.btMaximize.visibility = View.GONE
                if (binding.vdhv.isMinimized) {
                    binding.btMinimizeTopRight.visibility = View.VISIBLE
                    binding.btMinimizeTopLeft.visibility = View.VISIBLE
                }
            } else {
                binding.vdhv.isEnableRevertMaxSize = true
                binding.btMaximize.visibility = View.VISIBLE
                binding.btMinimizeTopRight.visibility = View.GONE
                binding.btMinimizeTopLeft.visibility = View.GONE
            }
        }
        binding.btAppear.setSafeOnClickListener {
            binding.vdhv.appear()
        }
        binding.btDisappear.setSafeOnClickListener {
            binding.vdhv.dissappear()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onStateChange(state: VDHView.State) {
        binding.tv0.text = "onStateChange: " + state.name
    }

    @SuppressLint("SetTextI18n")
    override fun onPartChange(part: VDHView.Part) {
        binding.tv2.text = "onPartChange: " + part.name
    }

    @SuppressLint("SetTextI18n")
    override fun onViewPositionChanged(
        left: Int,
        top: Int,
        dragOffset: Float
    ) {
        binding.tv1.text = "onViewPositionChanged left: $left, top: $top, dragOffset: $dragOffset"
    }

    override fun onOverScroll(state: VDHView.State?, part: VDHView.Part?) {
        binding.vdhv.dissappear()
    }

    override fun onPause() {
        super.onPause()
        binding.vdhv.onPause()
    }
}
