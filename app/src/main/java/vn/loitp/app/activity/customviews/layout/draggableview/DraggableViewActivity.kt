package vn.loitp.app.activity.customviews.layout.draggableview

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.layout.draggablepanel.DraggableListener
import com.views.layout.draggablepanel.DraggableView
import vn.loitp.app.R

class DraggableViewActivity : BaseFontActivity() {
    private lateinit var draggableView: DraggableView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        draggableView = findViewById(R.id.draggable_view)

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

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_draggable_view
    }
}
