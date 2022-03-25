package vn.loitp.app.activity.customviews.dialog.customdialog

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_dialog_custom.*
import vn.loitp.app.R

@LogTag("CustomDialogActivity")
@IsFullScreen(false)
class CustomDialogActivity : BaseFontActivity() {

    private var isFullScreen: Boolean = false

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dialog_custom
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btToggleFullScreen.setSafeOnClickListener {
            isFullScreen = !isFullScreen
            LScreenUtil.toggleFullscreen(activity = this, isFullScreen = isFullScreen)
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
