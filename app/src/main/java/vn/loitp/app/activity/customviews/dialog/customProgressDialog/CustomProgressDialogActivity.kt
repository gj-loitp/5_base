package vn.loitp.app.activity.customviews.dialog.customProgressDialog

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_custom_progress_dialog.*
import vn.loitp.app.R

@LogTag("CustomProgressDialogActivity")
@IsFullScreen(false)
class CustomProgressDialogActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_progress_dialog
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
            this.ivIconRight?.isVisible = false
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
