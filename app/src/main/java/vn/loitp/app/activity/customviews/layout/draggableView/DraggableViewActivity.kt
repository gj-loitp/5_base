package vn.loitp.app.activity.customviews.layout.draggableView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.layout.draggablePanel.DraggableListener
import kotlinx.android.synthetic.main.activity_draggable_view.*
import vn.loitp.app.R

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
            this.viewShadow?.isVisible = true
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
