package vn.loitp.app.activity.customviews.dialog.customdialog

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_dialog_custom.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_dialog_custom)
@LogTag("CustomDialogActivity")
@IsFullScreen(false)
class CustomDialogActivity : BaseFontActivity() {

    private var isFullScreen: Boolean = false

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
            PositionDialog().showImmersivePos(activity = this, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.DEFAULT)
        }
        btTopLeft.setSafeOnClickListener {
            PositionDialog().showImmersivePos(activity = this, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_LEFT)
        }
        btTopCenter.setSafeOnClickListener {
            PositionDialog().showImmersivePos(activity = this, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_CENTER)
        }
        btTopRight.setSafeOnClickListener {
            PositionDialog().showImmersivePos(activity = this, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_RIGHT)
        }
        btCenterLeft.setSafeOnClickListener {
            PositionDialog().showImmersivePos(activity = this, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.CENTER_LEFT)
        }
        btCenterCenter.setSafeOnClickListener {
            PositionDialog().showImmersivePos(activity = this, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.CENTER_CENTER)
        }
        btCenterRight.setSafeOnClickListener {
            PositionDialog().showImmersivePos(activity = this, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.CENTER_RIGHT)
        }
        btBottomLeft.setSafeOnClickListener {
            PositionDialog().showImmersivePos(activity = this, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.BOTTOM_LEFT)
        }
        btBottomCenter.setSafeOnClickListener {
            PositionDialog().showImmersivePos(activity = this, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.BOTTOM_CENTER)
        }
        btBottomRight.setSafeOnClickListener {
            PositionDialog().showImmersivePos(activity = this, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.BOTTOM_RIGHT)
        }
    }

}
