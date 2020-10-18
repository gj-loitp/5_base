package vn.loitp.app.activity.customviews.dialog.prettydialog

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.dialog.prettydialog.PrettyDialog
import kotlinx.android.synthetic.main.activity_dialog_pretty.*
import vn.loitp.app.R

//https://github.com/mjn1369/PrettyDialog

@LayoutId(R.layout.activity_dialog_pretty)
@LogTag("PrettyDialogActivity")
@IsFullScreen(false)
class PrettyDialogActivity : BaseFontActivity(), OnClickListener {
    private var prettyDialog: PrettyDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btShow1.setOnClickListener(this)
        btShow2.setOnClickListener(this)
        btShow3.setOnClickListener(this)
        btShow4.setOnClickListener(this)
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
        prettyDialog = PrettyDialog(this)
        prettyDialog?.setTitle("PrettyDialog Title")?.setMessage("PrettyDialog Message")?.show()
    }

    private fun show2() {
        prettyDialog = PrettyDialog(this)
        prettyDialog?.apply {
            setTitle("PrettyDialog Title")
                    .setMessage("PrettyDialog Message")
                    .setIcon(R.drawable.ic_info_black_48dp)
                    .setIconTint(R.color.green)
                    .setIconCallback(Runnable {
                        showShortInformation("onClick setIconCallback")
                        prettyDialog?.cancel()
                    })
                    .show()
        }
    }

    private fun show3() {
        prettyDialog = PrettyDialog(this)
        // button OnClick listener
        prettyDialog?.apply {
            setTitle("PrettyDialog Title")
                    .setMessage("PrettyDialog Message")
                    .setIcon(R.drawable.ic_info_black_48dp)
                    .setIconTint(R.color.green)
                    .setIconCallback(Runnable {
                        showShortInformation("onClick setIconCallback")
                    })
                    // OK button
                    .addButton(
                            "OK", // button text
                            R.color.white,
                            R.color.green,
                            Runnable {
                                showShortInformation("onClick OK")
                                prettyDialog?.cancel()
                            }
                    )
                    //Cancel button
                    .addButton(
                            "Cancel",
                            R.color.white,
                            R.color.red,
                            Runnable {
                                showShortInformation("onClick Cancel")
                                prettyDialog?.cancel()
                            }
                    )
                    // 3rd button
                    .addButton(
                            "Option 3",
                            R.color.black,
                            R.color.gray,
                            Runnable {
                                showShortInformation("onClick Option 3")
                                prettyDialog?.cancel()
                            }
                    )
                    .show()
        }
    }

    private fun show4() {
        prettyDialog = PrettyDialog(this)
        prettyDialog?.apply {
            setTitle("PrettyDialog Title")
                    .setTitleColor(R.color.blue)
                    .setMessage("By agreeing to our terms and conditions, you agree to our terms and conditions.")
                    .setMessageColor(R.color.gray)
                    .setTypeface(Typeface.createFromAsset(resources.assets, "fonts/android_font.TTF"))
                    .setAnimationEnabled(true)
                    .setIcon(R.drawable.ic_info_black_48dp)
                    .setIconTint(R.color.green)
                    .setTextSizeTitle(resources.getDimension(R.dimen.txt_medium))
                    .setTextSizeMsg(resources.getDimension(R.dimen.txt_medium))
                    .setIconCallback(Runnable {
                        showShortInformation("onClick setIconCallback")
                    })
                    // OK button
                    .addButton(
                            "OK", // button text
                            R.color.white,
                            R.color.green,
                            Runnable {
                                showShortInformation("onClick OK")
                                prettyDialog?.cancel()
                            }
                    )
                    //Cancel button
                    .addButton(
                            "Cancel",
                            R.color.white,
                            R.color.red,
                            Runnable {
                                showShortInformation("onClick Cancel")
                                prettyDialog?.cancel()
                            }
                    )
                    // 3rd button
                    .addButton(
                            "Option 3",
                            R.color.black,
                            R.color.gray,
                            Runnable {
                                showShortInformation("onClick Option 3")
                                prettyDialog?.cancel()
                            }
                    )
                    .setTextSizeButton(resources.getDimension(R.dimen.txt_medium))
                    .show()
        }
    }

    override fun onDestroy() {
        prettyDialog?.cancel()
        super.onDestroy()
    }
}
