package vn.loitp.app.activity.function.viewDragHelperSimple

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_func_view_draghelper_simple_1.*
import vn.loitp.app.R

@LogTag("ViewDragHelperSimpleActivity1")
@IsFullScreen(false)
class ViewDragHelperSimpleActivity1 : BaseFontActivity(), VDHView.Callback {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_view_draghelper_simple_1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        vdhv.setCallback(this)

        btToast.setSafeOnClickListener {
            showShortInformation("Click")
        }
        btMaximize.setSafeOnClickListener {
            vdhv.maximize()
        }
        btMinimizeBottomLeft.setSafeOnClickListener {
            vdhv.minimizeBottomLeft()
        }
        btMinimizeBottomRight.setSafeOnClickListener {
            vdhv.minimizeBottomRight()
        }
        btMinimizeTopRight.setSafeOnClickListener {
            vdhv.minimizeTopRight()
        }
        btMinimizeTopLeft.setSafeOnClickListener {
            vdhv.minimizeTopLeft()
        }
        btAlpha.setSafeOnClickListener {
            vdhv.isEnableAlpha = !vdhv.isEnableAlpha
        }
        btShowHideHeader.setSafeOnClickListener {
            vdhv.toggleShowHideHeaderView()
        }
        btShowHideBody.setSafeOnClickListener {
            vdhv.toggleShowHideBodyView()
        }
        btSlideToPosition.setSafeOnClickListener {
            vdhv.smoothSlideTo(positionX = 300, positionY = 600)
        }
        btRevertMax.setSafeOnClickListener {
            if (vdhv.isEnableRevertMaxSize) {
                vdhv.isEnableRevertMaxSize = false
                btMaximize.visibility = View.GONE
                if (vdhv.isMinimized) {
                    btMinimizeTopRight.visibility = View.VISIBLE
                    btMinimizeTopLeft.visibility = View.VISIBLE
                }
            } else {
                vdhv.isEnableRevertMaxSize = true
                btMaximize.visibility = View.VISIBLE
                btMinimizeTopRight.visibility = View.GONE
                btMinimizeTopLeft.visibility = View.GONE
            }
        }
        btAppear.setSafeOnClickListener {
            vdhv.appear()
        }
        btDisappear.setSafeOnClickListener {
            vdhv.dissappear()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onStateChange(state: VDHView.State) {
        tv0.text = "onStateChange: " + state.name
    }

    @SuppressLint("SetTextI18n")
    override fun onPartChange(part: VDHView.Part) {
        tv2.text = "onPartChange: " + part.name
    }

    @SuppressLint("SetTextI18n")
    override fun onViewPositionChanged(left: Int, top: Int, dragOffset: Float) {
        tv1.text = "onViewPositionChanged left: $left, top: $top, dragOffset: $dragOffset"
    }

    override fun onOverScroll(state: VDHView.State?, part: VDHView.Part?) {
        vdhv.dissappear()
    }

    override fun onPause() {
        super.onPause()
        vdhv.onPause()
    }
}
