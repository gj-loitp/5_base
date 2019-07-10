package vn.loitp.app.activity.customviews.dialog.prettydialog

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.View.OnClickListener
import com.core.base.BaseFontActivity
import com.views.LToast
import com.views.dialog.prettydialog.PrettyDialog
import loitp.basemaster.R

//https://github.com/mjn1369/PrettyDialog
class PrettyDialogActivity : BaseFontActivity(), OnClickListener {
    private var prettyDialog: PrettyDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_show_1).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_2).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_3).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_4).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dialog_pretty
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_show_1 -> show1()
            R.id.bt_show_2 -> show2()
            R.id.bt_show_3 -> show3()
            R.id.bt_show_4 -> show4()
        }
    }

    private fun show1() {
        prettyDialog = PrettyDialog(activity)
        prettyDialog?.setTitle("PrettyDialog Title")?.setMessage("PrettyDialog Message")?.show()
    }

    private fun show2() {
        prettyDialog = PrettyDialog(activity)
        prettyDialog?.apply {
            setTitle("PrettyDialog Title")
                    .setMessage("PrettyDialog Message")
                    .setIcon(R.drawable.pdlg_icon_info)
                    .setIconTint(R.color.pdlg_color_green)
                    .setIconCallback {
                        LToast.show(activity, "onClick setIconCallback")
                        prettyDialog?.cancel()
                    }
                    .show()
        }
    }

    private fun show3() {
        prettyDialog = PrettyDialog(activity)
        // button OnClick listener
        prettyDialog?.apply {
            setTitle("PrettyDialog Title")
                    .setMessage("PrettyDialog Message")
                    .setIcon(R.drawable.pdlg_icon_info)
                    .setIconTint(R.color.pdlg_color_green)
                    .setIconCallback { LToast.show(activity, "onClick setIconCallback") }
                    // OK button
                    .addButton(
                            "OK", // button text
                            R.color.pdlg_color_white, // button text color
                            R.color.pdlg_color_green // button background color
                    ) {
                        LToast.show(activity, "onClick OK")
                        prettyDialog?.cancel()
                    }
                    //Cancel button
                    .addButton(
                            "Cancel",
                            R.color.pdlg_color_white,
                            R.color.pdlg_color_red
                    ) {
                        LToast.show(activity, "onClick Cancel")
                        prettyDialog?.cancel()
                    }
                    // 3rd button
                    .addButton(
                            "Option 3",
                            R.color.pdlg_color_black,
                            R.color.pdlg_color_gray
                    ) {
                        LToast.show(activity, "onClick Option 3")
                        prettyDialog?.cancel()
                    }
                    .show()
        }
    }

    private fun show4() {
        prettyDialog = PrettyDialog(activity)
        prettyDialog?.apply {
            setTitle("PrettyDialog Title")
                    .setTitleColor(R.color.pdlg_color_blue)
                    .setMessage("By agreeing to our terms and conditions, you agree to our terms and conditions.")
                    .setMessageColor(R.color.pdlg_color_gray)
                    .setTypeface(Typeface.createFromAsset(resources.assets, "fonts/OpenSans-Bold.ttf"))
                    .setAnimationEnabled(true)
                    .setIcon(R.drawable.pdlg_icon_info)
                    .setIconTint(R.color.pdlg_color_green)
                    .setTextSizeTitle(TypedValue.COMPLEX_UNIT_DIP, 22)
                    .setTextSizeMsg(TypedValue.COMPLEX_UNIT_DIP, 18)
                    .setIconCallback { LToast.show(activity, "onClick setIconCallback") }
                    // OK button
                    .addButton(
                            "OK", // button text
                            R.color.pdlg_color_white, // button text color
                            R.color.pdlg_color_green // button background color
                    ) {
                        LToast.show(activity, "onClick OK")
                        prettyDialog?.cancel()
                    }
                    //Cancel button
                    .addButton(
                            "Cancel",
                            R.color.pdlg_color_white,
                            R.color.pdlg_color_red
                    ) {
                        LToast.show(activity, "onClick Cancel")
                        prettyDialog?.cancel()
                    }
                    // 3rd button
                    .addButton(
                            "Option 3",
                            R.color.pdlg_color_black,
                            R.color.pdlg_color_gray
                    ) {
                        LToast.show(activity, "onClick Option 3")
                        prettyDialog?.cancel()
                    }
                    .setTextSizeButton(TypedValue.COMPLEX_UNIT_DIP, 20)
                    .show()
        }
    }

    override fun onDestroy() {
        prettyDialog?.cancel()
        super.onDestroy()
    }
}
