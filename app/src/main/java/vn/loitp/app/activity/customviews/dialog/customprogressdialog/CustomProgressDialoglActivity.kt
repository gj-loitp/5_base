package vn.loitp.app.activity.customviews.dialog.customprogressdialog

import android.os.Bundle
import android.view.View
import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LDialogUtil
import vn.loitp.core.utilities.LUIUtil

class CustomProgressDialoglActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_0).setOnClickListener {
            val alertDialog = LDialogUtil.showCustomProgressDialog(activity, 0.1f)
            LUIUtil.setDelay(3000) { mls ->
                alertDialog?.dismiss()
            }
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_progress_dialog
    }
}
