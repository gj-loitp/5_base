package vn.loitp.app.activity.function.fullscreen

import android.os.Bundle
import android.view.View
import com.core.base.BaseActivity
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.views.dialog.imersivedialog.ImmersiveDialogFragment
import loitp.basemaster.R

class FullScreenActivity : BaseFontActivity(), View.OnClickListener {

    private var isFullScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_toggle_fullscreen).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_dialog).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fullscreen
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_toggle_fullscreen -> {
                isFullScreen = !isFullScreen
                LScreenUtil.toggleFullscreen(activity, isFullScreen)
            }
            R.id.bt_show_dialog -> showDialog()
        }
    }

    private fun showDialog() {
        ImmersiveDialogFragment().showImmersive(activity)
    }
}
