package vn.loitp.up.a.cv.layout.draggableView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.layout.draggablePanel.DraggableListener
import vn.loitp.databinding.ADraggableViewBinding

@LogTag("DraggableViewActivity")
@IsFullScreen(false)
class DraggableViewActivity : BaseActivityFont() {
    private lateinit var binding: ADraggableViewBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADraggableViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = DraggableViewActivity::class.java.simpleName
        }
        binding.draggableView.isClickToMaximizeEnabled = true
        binding.draggableView.isClickToMinimizeEnabled = true
        binding.draggableView.setHorizontalAlphaEffectEnabled(true)

        binding.draggableView.setDraggableListener(object : DraggableListener {
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
