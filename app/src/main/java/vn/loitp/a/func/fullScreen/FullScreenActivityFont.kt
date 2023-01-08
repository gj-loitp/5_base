package vn.loitp.a.func.fullScreen

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.utilities.LScreenUtil
import com.loitp.views.dlg.imersiveDialog.ImmersiveDialogFragment
import kotlinx.android.synthetic.main.a_func_fullscreen.*
import vn.loitp.R

@LogTag("FullScreenActivity")
@IsFullScreen(false)
class FullScreenActivityFont : BaseActivityFont(), View.OnClickListener {

    private var isFullScreen: Boolean = false

    override fun setLayoutResourceId(): Int {
        return R.layout.a_func_fullscreen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = FullScreenActivityFont::class.java.simpleName
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
