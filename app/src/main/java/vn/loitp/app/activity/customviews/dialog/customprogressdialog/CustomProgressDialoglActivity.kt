package vn.loitp.app.activity.customviews.dialog.customprogressdialog

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_dialog_custom_progress.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_dialog_custom_progress)
@LogTag("CustomProgressDialoglActivity")
@IsFullScreen(false)
class CustomProgressDialoglActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt0.setSafeOnClickListener {
            val alertDialog = LDialogUtil.showCustomProgressDialog(context = this, amount = 0.1f)
            LUIUtil.setDelay(mls = 3000, runnable = Runnable {
                alertDialog?.dismiss()
            })
        }
    }

}
