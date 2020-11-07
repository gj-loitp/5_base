package vn.loitp.app.activity.customviews.layout.draggablepanelfree

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_draggable_panel_free.*
import vn.loitp.app.R

@LogTag("DraggablePanelFreeActivity")
@IsFullScreen(false)
class DraggablePanelFreeActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_draggable_panel_free
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btMaximize.setSafeOnClickListener {
            dpfl.maximize()
        }
        btMinimize.setSafeOnClickListener {
            dpfl.minimize()
        }
        dpfl?.setCallback { state ->
            tvState.text = "onStateChange " + state.name
        }
    }

}
