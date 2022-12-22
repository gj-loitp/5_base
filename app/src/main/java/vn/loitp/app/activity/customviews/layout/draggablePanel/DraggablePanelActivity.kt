package vn.loitp.app.activity.customviews.layout.draggablePanel

import android.content.res.Resources
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.layout.draggablePanel.DraggableListener
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
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = DraggablePanelActivity::class.java.simpleName
        }
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
