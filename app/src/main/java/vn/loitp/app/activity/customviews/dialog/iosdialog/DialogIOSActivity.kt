package vn.loitp.app.activity.customviews.dialog.iosdialog

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LDialogUtil
import vn.loitp.views.LToast

class DialogIOSActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_show_1).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_2).setOnClickListener(this)
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
        when (v.id) {
            R.id.bt_show_1 -> show1()
            R.id.bt_show_2 -> show2()
        }
    }

    private fun show1() {
        LDialogUtil.showIOSDialog1(activity, "Allow \"Calendar\" to access your location while you use the app?", "This is a subtitle", "Allow", false) { LToast.show(activity, "onClick1") }
    }

    private fun show2() {
        LDialogUtil.showIOSDialog2(activity, "Allow \"Calendar\" to access your location while you use the app?", "This is a subtitle", "Don't Allow", "Allow", true, object : LDialogUtil.Callback2 {
            override fun onClick1() {
                LToast.show(activity, "onClick1")
            }

            override fun onClick2() {
                LToast.show(activity, "onClick2")
            }
        })
    }
}
