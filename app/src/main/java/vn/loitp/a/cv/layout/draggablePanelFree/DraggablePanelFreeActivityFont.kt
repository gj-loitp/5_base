package vn.loitp.a.cv.layout.draggablePanelFree

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_draggable_panel_free.*
import vn.loitp.R

@LogTag("DraggablePanelFreeActivity")
@IsFullScreen(false)
class DraggablePanelFreeActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_draggable_panel_free
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = DraggablePanelFreeActivityFont::class.java.simpleName
        }
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
