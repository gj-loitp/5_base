package vn.loitp.app.a.customviews.layout.draggableView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.layout.draggablePanel.DraggableListener
import kotlinx.android.synthetic.main.activity_draggable_view.*
import vn.loitp.R

@LogTag("DraggableViewActivity")
@IsFullScreen(false)
class DraggableViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_draggable_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = DraggableViewActivity::class.java.simpleName
        }
        draggableView.isClickToMaximizeEnabled = true
        draggableView.isClickToMinimizeEnabled = true
        draggableView.setHorizontalAlphaEffectEnabled(true)

        draggableView.setDraggableListener(object : DraggableListener {
            override fun onMaximized() {
                logD("onMaximized")
            }

            override fun onMinimized() {
                logD("onMinimized")
            }

            override fun onClosedToLeft() {
                logD("onClosedToLeft")
            }

            override fun onClosedToRight() {
                logD("onClosedToRight")
            }
        })
    }
}
