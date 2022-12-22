package vn.loitp.app.a.function.fullScreen

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.dlg.imersiveDialog.ImmersiveDialogFragment
import kotlinx.android.synthetic.main.activity_func_fullscreen.*
import vn.loitp.R

@LogTag("FullScreenActivity")
@IsFullScreen(false)
class FullScreenActivity : BaseFontActivity(), View.OnClickListener {

    private var isFullScreen: Boolean = false

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_fullscreen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = FullScreenActivity::class.java.simpleName
        }
        btToggleFullScreen.setOnClickListener(this)
        btShowDialog.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btToggleFullScreen -> {
                isFullScreen = !isFullScreen
                LScreenUtil.toggleFullscreen(this, isFullScreen)
            }
            btShowDialog -> showDialog()
        }
    }

    private fun showDialog() {
        ImmersiveDialogFragment().showImmersive(this)
    }
}
