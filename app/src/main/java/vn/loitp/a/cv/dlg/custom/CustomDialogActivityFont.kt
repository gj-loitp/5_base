package vn.loitp.a.cv.dlg.custom

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.toggleFullscreen
import kotlinx.android.synthetic.main.a_dlg_custom.*
import vn.loitp.R

@LogTag("CustomDialogActivity")
@IsFullScreen(false)
class CustomDialogActivityFont : BaseActivityFont() {

    private var isFullScreen: Boolean = false

    override fun setLayoutResourceId(): Int {
        return R.layout.a_dlg_custom
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = CustomDialogActivityFont::class.java.simpleName
        }
        btToggleFullScreen.setSafeOnClickListener {
            isFullScreen = !isFullScreen
            this.toggleFullscreen(isFullScreen = isFullScreen)
        }
        btDefault.setSafeOnClickListener {
            PositionDialog().showImmersivePos(
                activity = this,
                anchorView = btAnchorView,
                sizeWidthPx = null,
                sizeHeightPx = null,
            )
        }
    }
}
