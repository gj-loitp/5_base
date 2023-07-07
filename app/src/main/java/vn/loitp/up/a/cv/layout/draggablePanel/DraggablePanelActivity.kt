package vn.loitp.up.a.cv.layout.draggablePanel

import android.content.res.Resources
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.layout.draggablePanel.DraggableListener
import vn.loitp.databinding.ADraggablePanelBinding

@LogTag("DraggablePanelActivity")
@IsFullScreen(false)
class DraggablePanelActivity : BaseActivityFont() {
    private lateinit var binding: ADraggablePanelBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADraggablePanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = DraggablePanelActivity::class.java.simpleName
        }
        initializeDraggablePanel()
        binding.draggablePanel.setDraggableListener(object : DraggableListener {
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

        binding.draggablePanel.apply {
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
