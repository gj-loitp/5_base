package vn.loitp.app.activity.customviews.dialog.customprogressdialog

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_dialog_custom_progress.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_dialog_custom_progress)
class CustomProgressDialoglActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt0.setOnClickListener {
            val alertDialog = LDialogUtil.showCustomProgressDialog(context = activity, amount = 0.1f)
            LUIUtil.setDelay(mls = 3000, runnable = Runnable {
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

}
