package vn.loitp.app.activity.customviews.dialog.customdialog

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.activity_custom_dialog.*
import loitp.basemaster.R

class CustomDialogActivity : BaseFontActivity() {

    private var isFullScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btToggleFullScreen.setOnClickListener { v ->
            isFullScreen = !isFullScreen
            LScreenUtil.toggleFullscreen(activity = activity, isFullScreen = isFullScreen)
        }
        btTopLeft.setOnClickListener { v ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = anchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_LEFT)
        }
        btTopCenter.setOnClickListener { v ->
            PositionDialog().showImmersivePos(activity = activity, anchorView = anchorView, sizeWidthPx = null, sizeHeightPx = null, position = PositionDialog.Position.TOP_CENTER)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return "TAG" + javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_dialog
    }
}
