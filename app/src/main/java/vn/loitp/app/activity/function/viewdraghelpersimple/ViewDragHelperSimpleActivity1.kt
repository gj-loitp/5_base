package vn.loitp.app.activity.function.viewdraghelpersimple

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_func_view_draghelper_simple_1.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_func_view_draghelper_simple_1)
@LogTag("ViewDragHelperSimpleActivity1")
class ViewDragHelperSimpleActivity1 : BaseFontActivity(), VDHView.Callback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vdhv.setCallback(this)

        btToast.setOnClickListener {
            showShort("Click")
        }
        btMaximize.setOnClickListener {
            vdhv.maximize()
        }
        btMinimizeBottomLeft.setOnClickListener {
            vdhv.minimizeBottomLeft()
        }
        btMinimizeBottomRight.setOnClickListener {
            vdhv.minimizeBottomRight()
        }
        btMinimizeTopRight.setOnClickListener {
            vdhv.minimizeTopRight()
        }
        btMinimizeTopLeft.setOnClickListener {
            vdhv.minimizeTopLeft()
        }
        btAlpha.setOnClickListener {
            vdhv.isEnableAlpha = !vdhv.isEnableAlpha
        }
        btShowHideHeader.setOnClickListener {
            vdhv.toggleShowHideHeaderView()
        }
        btShowHideBody.setOnClickListener {
            vdhv.toggleShowHideBodyView()
        }
        btSlideToPosition.setOnClickListener {
            vdhv.smoothSlideTo(positionX = 300, positionY = 600)
        }
        btRevertMax.setOnClickListener {
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
        btAppear.setOnClickListener {
            vdhv.appear()
        }
        btDisappear.setOnClickListener {
            vdhv.dissappear()
        }
    }

    override fun setFullScreen(): Boolean {
        return false
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
