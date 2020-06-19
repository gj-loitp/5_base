package vn.loitp.app.activity.customviews.dialog.customdialog

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.activity_dialog_custom.*
import vn.loitp.app.R

class CustomDialogActivity : BaseFontActivity() {

    private var isFullScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btToggleFullScreen.setOnClickListener { _ ->
            isFullScreen = !isFullScreen
            LScreenUtil.toggleFullscreen(activity = activity, isFullScreen = isFullScreen)
        }
        btDefault.setOnClickListener { _ ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.DEFAULT)
        }
        btTopLeft.setOnClickListener { _ ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_LEFT)
        }
        btTopCenter.setOnClickListener { _ ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_CENTER)
        }
        btTopRight.setOnClickListener { _ ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_RIGHT)
        }
        btCenterLeft.setOnClickListener { _ ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.CENTER_LEFT)
        }
        btCenterCenter.setOnClickListener { _ ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.CENTER_CENTER)
        }
        btCenterRight.setOnClickListener { _ ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.CENTER_RIGHT)
        }
        btBottomLeft.setOnClickListener { _ ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.BOTTOM_LEFT)
        }
        btBottomCenter.setOnClickListener { _ ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.BOTTOM_CENTER)
        }
        btBottomRight.setOnClickListener { _ ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = btAnchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.BOTTOM_RIGHT)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dialog_custom
    }
}
