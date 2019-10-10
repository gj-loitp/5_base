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
        btShowDialog1.setOnClickListener { v ->
            PositionDialog().showImmersiveCenter(activity = activity, sizeWidthPx = null, sizeHeightPx = null)
        }
        btShowDialog2.setOnClickListener { v ->
            val posX = v.right
            val posY = v.bottom
            PositionDialog().showImmersivePos(activity = activity, posX = posX, posY = posY, sizeWidthPx = null, sizeHeightPx = null)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_dialog
    }
}
