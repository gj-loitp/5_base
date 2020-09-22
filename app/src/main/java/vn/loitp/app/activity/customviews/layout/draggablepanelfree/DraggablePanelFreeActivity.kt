package vn.loitp.app.activity.customviews.layout.draggablepanelfree

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_draggable_panel_free.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_draggable_panel_free)
@LogTag("DraggablePanelFreeActivity")
@IsFullScreen(false)
class DraggablePanelFreeActivity : BaseFontActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btMaximize.setOnClickListener {
            dpfl.maximize()
        }
        bt_minimize.setOnClickListener {
            dpfl.minimize()
        }
        dpfl?.setCallback { state ->
            tv_state.text = "onStateChange " + state.name
        }
    }

}
