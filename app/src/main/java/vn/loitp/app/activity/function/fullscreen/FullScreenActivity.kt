package vn.loitp.app.activity.function.fullscreen

import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.views.dialog.imersivedialog.ImmersiveDialogFragment
import kotlinx.android.synthetic.main.activity_func_fullscreen.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_func_fullscreen)
@LogTag("FullScreenActivity")
class FullScreenActivity : BaseFontActivity(), View.OnClickListener {

    private var isFullScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btToggleFullScreen.setOnClickListener(this)
        btShowDialog.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        when (v) {
            btToggleFullScreen -> {
                isFullScreen = !isFullScreen
                LScreenUtil.toggleFullscreen(activity, isFullScreen)
            }
            btShowDialog -> showDialog()
        }
    }

    private fun showDialog() {
        ImmersiveDialogFragment().showImmersive(activity)
    }
}
