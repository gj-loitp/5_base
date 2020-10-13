package vn.loitp.app.activity.customviews.layout.draggableview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.layout.draggablepanel.DraggableListener
import kotlinx.android.synthetic.main.activity_draggable_view.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_draggable_view)
@LogTag("DraggableViewActivity")
@IsFullScreen(false)
class DraggableViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
