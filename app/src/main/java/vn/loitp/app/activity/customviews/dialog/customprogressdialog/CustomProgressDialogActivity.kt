package vn.loitp.app.activity.customviews.dialog.customprogressdialog

import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_dialog_custom_progress.*
import vn.loitp.app.R

@LogTag("CustomProgressDialogActivity")
@IsFullScreen(false)
class CustomProgressDialogActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dialog_custom_progress
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = CustomProgressDialogActivity::class.java.simpleName
        }
        bt0.setSafeOnClickListener {
            showDialogProgress()
            LUIUtil.setDelay(mls = 4000, runnable = {
                hideDialogProgress()
            })
        }
    }
}
