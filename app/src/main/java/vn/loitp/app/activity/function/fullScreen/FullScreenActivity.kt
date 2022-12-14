package vn.loitp.app.activity.function.fullScreen

import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.dialog.imersiveDialog.ImmersiveDialogFragment
import kotlinx.android.synthetic.main.activity_func_fullscreen.*
import vn.loitp.app.R

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
