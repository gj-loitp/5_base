package vn.loitp.app.activity.customviews.dialog.iosdialog

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import com.views.LToast
import loitp.basemaster.R

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
        LDialogUtil.showIOSDialog1(activity, "Allow \"Calendar\" to access your location while you use the app?",
                "This is a subtitle", "Allow", false, object : LDialogUtil.Callback1 {
            override fun onClick1() {
                LToast.show(activity, "onClick1", R.drawable.bkg_horizontal)
            }
        })
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
