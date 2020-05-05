package vn.loitp.app.activity.customviews.dialog.prettydialog

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.View.OnClickListener
import com.core.base.BaseFontActivity
import com.views.dialog.prettydialog.PrettyDialog
import kotlinx.android.synthetic.main.activity_dialog_pretty.*
import vn.loitp.app.R

//https://github.com/mjn1369/PrettyDialog
class PrettyDialogActivity : BaseFontActivity(), OnClickListener {
    private var prettyDialog: PrettyDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btShow1.setOnClickListener(this)
        btShow2.setOnClickListener(this)
        btShow3.setOnClickListener(this)
        btShow4.setOnClickListener(this)
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
        when (v) {
            btShow1 -> show1()
            btShow2 -> show2()
            btShow3 -> show3()
            btShow4 -> show4()
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
                    .setIconTint(R.color.green)
                    .setIconCallback(Runnable {
                        showShort("onClick setIconCallback")
                        prettyDialog?.cancel()
                    })
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
                    .setIconTint(R.color.green)
                    .setIconCallback(Runnable {
                        showShort("onClick setIconCallback")
                    })
                    // OK button
                    .addButton(
                            "OK", // button text
                            R.color.white,
                            R.color.green,
                            Runnable {
                                showShort("onClick OK")
                                prettyDialog?.cancel()
                            }
                    )
                    //Cancel button
                    .addButton(
                            "Cancel",
                            R.color.white,
                            R.color.red,
                            Runnable {
                                showShort("onClick Cancel")
                                prettyDialog?.cancel()
                            }
                    )
                    // 3rd button
                    .addButton(
                            "Option 3",
                            R.color.black,
                            R.color.gray,
                            Runnable {
                                showShort("onClick Option 3")
                                prettyDialog?.cancel()
                            }
                    )
                    .show()
        }
    }

    private fun show4() {
        prettyDialog = PrettyDialog(activity)
        prettyDialog?.apply {
            setTitle("PrettyDialog Title")
                    .setTitleColor(R.color.blue)
                    .setMessage("By agreeing to our terms and conditions, you agree to our terms and conditions.")
                    .setMessageColor(R.color.gray)
                    .setTypeface(Typeface.createFromAsset(resources.assets, "fonts/android_font.TTF"))
                    .setAnimationEnabled(true)
                    .setIcon(R.drawable.pdlg_icon_info)
                    .setIconTint(R.color.green)
                    .setTextSizeTitle(TypedValue.COMPLEX_UNIT_DIP, 22)
                    .setTextSizeMsg(TypedValue.COMPLEX_UNIT_DIP, 18)
                    .setIconCallback(Runnable {
                        showShort("onClick setIconCallback")
                    })
                    // OK button
                    .addButton(
                            "OK", // button text
                            R.color.white,
                            R.color.green,
                            Runnable {
                                showShort("onClick OK")
                                prettyDialog?.cancel()
                            }
                    )
                    //Cancel button
                    .addButton(
                            "Cancel",
                            R.color.white,
                            R.color.red,
                            Runnable {
                                showShort("onClick Cancel")
                                prettyDialog?.cancel()
                            }
                    )
                    // 3rd button
                    .addButton(
                            "Option 3",
                            R.color.black,
                            R.color.gray,
                            Runnable {
                                showShort("onClick Option 3")
                                prettyDialog?.cancel()
                            }
                    )
                    .setTextSizeButton(TypedValue.COMPLEX_UNIT_DIP, 20)
                    .show()
        }
    }

    override fun onDestroy() {
        prettyDialog?.cancel()
        super.onDestroy()
    }
}
