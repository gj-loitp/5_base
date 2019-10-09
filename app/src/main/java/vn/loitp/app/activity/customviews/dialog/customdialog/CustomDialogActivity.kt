package vn.loitp.app.activity.customviews.dialog.customdialog

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.views.dialog.imersivedialog.ImmersiveDialogFragment
import kotlinx.android.synthetic.main.activity_custom_dialog.*
import loitp.basemaster.R

class CustomDialogActivity : BaseFontActivity() {

    private var isFullScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btToggleFullScreen.setOnClickListener { v ->
            isFullScreen = !isFullScreen
            LScreenUtil.toggleFullscreen(activity, isFullScreen)
        }
        btShowDialog1.setOnClickListener { v ->
            PositionDialog().showImmersive(activity)
            //ImmersiveDialogFragment().showImmersive(activity)
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
