package vn.loitp.app.activity.customviews.dialog.customprogressdialog

import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import vn.loitp.app.R

class CustomProgressDialoglActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt0).setOnClickListener {
            val alertDialog = LDialogUtil.showCustomProgressDialog(activity, 0.1f)
            LUIUtil.setDelay(3000, Runnable {
                alertDialog?.dismiss()
            })
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
