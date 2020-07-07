package vn.loitp.app.activity.customviews.dialog.iosdialog

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import com.interfaces.Callback1
import com.interfaces.Callback2
import kotlinx.android.synthetic.main.activity_dialog_ios.*
import vn.loitp.app.R

class DialogIOSActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btShow1.setOnClickListener(this)
        btShow2.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dialog_ios
    }

    override fun onClick(v: View) {
        when (v) {
            btShow1 -> show1()
            btShow2 -> show2()
        }
    }

    private fun show1() {
        LDialogUtil.showIOSDialog1(activity = activity,
                title = "Allow \"Calendar\" to access your location while you use the app?",
                subtitle = "This is a subtitle",
                label1 = "Allow",
                isBold = false,
                callback1 = object : Callback1 {
                    override fun onClick1() {
                        showShort("onClick1")
                    }
                })
    }

    private fun show2() {
        LDialogUtil.showIOSDialog2(activity = this.activity,
                title = "Allow \"Calendar\" to access your location while you use the app?",
                subtitle = "This is a subtitle",
                label1 = "Don't Allow",
                label2 = "Allow",
                isBold = true,
                callback2 = object : Callback2 {
                    override fun onClick1() {
                        showShort("onClick1")
                    }

                    override fun onClick2() {
                        showShort("onClick2")
                    }
                })
    }
}
