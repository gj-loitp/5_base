package vn.loitp.app.activity.customviews.dialog.customDialog

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_custom_dialog.*
import vn.loitp.app.R

@LogTag("CustomDialogActivity")
@IsFullScreen(false)
class CustomDialogActivity : BaseFontActivity() {

    private var isFullScreen: Boolean = false

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_dialog
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
