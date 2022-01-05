package vn.loitp.app.activity.customviews.layout.draggablepanel

import android.content.res.Resources
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.layout.draggablepanel.DraggableListener
import kotlinx.android.synthetic.main.activity_draggable_panel.*
import vn.loitp.app.R

@LogTag("DraggablePanelActivity")
@IsFullScreen(false)
class DraggablePanelActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_draggable_panel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        initializeDraggablePanel()
        draggablePanel.setDraggableListener(object : DraggableListener {
            override fun onMaximized() {}
            override fun onMinimized() {}
            override fun onClosedToLeft() {}
            override fun onClosedToRight() {}
        })
    }

    @Throws(Resources.NotFoundException::class)
    private fun initializeDraggablePanel() {
        val frmTop = FrmTestTop()
        val frmBottom = FrmTestBottom()

        draggablePanel?.apply {
            this.setFragmentManager(supportFragmentManager)
            this.setTopFragment(frmTop)
            this.setBottomFragment(frmBottom)

            // this.setXScaleFactor(xScaleFactor)
            // this.setYScaleFactor(yScaleFactor)
            this.setTopViewHeight(600)
            // this.setTopFragmentMarginRight(topViewMarginRight)
            // this.setTopFragmentMarginBottom(topViewMargnBottom)
            this.isClickToMaximizeEnabled = false
            this.isClickToMinimizeEnabled = false
            this.initializeView()
        }
    }
}
