package vn.loitp.app.activity.customviews.dialog.customdialog

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.activity_dialog_custom.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_dialog_custom)
@LogTag("CustomDialogActivity")
class CustomDialogActivity : BaseFontActivity() {

    private var isFullScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btToggleFullScreen.setOnClickListener {
            isFullScreen = !isFullScreen
            LScreenUtil.toggleFullscreen(activity = activity, isFullScreen = isFullScreen)
        }
        btDefault.setOnClickListener {
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.DEFAULT)
        }
        btTopLeft.setOnClickListener {
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_LEFT)
        }
        btTopCenter.setOnClickListener {
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_CENTER)
        }
        btTopRight.setOnClickListener {
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_RIGHT)
        }
        btCenterLeft.setOnClickListener {
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.CENTER_LEFT)
        }
        btCenterCenter.setOnClickListener {
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.CENTER_CENTER)
        }
        btCenterRight.setOnClickListener {
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.CENTER_RIGHT)
        }
        btBottomLeft.setOnClickListener {
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.BOTTOM_LEFT)
        }
        btBottomCenter.setOnClickListener {
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.BOTTOM_CENTER)
        }
        btBottomRight.setOnClickListener {
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.BOTTOM_RIGHT)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
