package vn.loitp.app.activity.customviews.dialog.customdialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.core.base.BaseActivity
import com.core.utilities.LUIUtil
import loitp.basemaster.R

class PositionDialog : DialogFragment() {
    private val TAG = javaClass.simpleName
    private var posX: Int? = null
    private var posY: Int? = null
    private var isAlignLeft = true
    private var isAlignTop = true

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //LLog.d(TAG, "onCreateDialog")
        val dialogBuilder = AlertDialog.Builder(context, R.style.FullDialogTheme)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_position, null)
        dialogBuilder.setView(dialogView)
        isCancelable = true
        init(dialogView)
        val d = dialogBuilder.create()
        d.window?.let { w ->
            w.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
            if (posX != null && posY != null) {
                w.attributes?.let { a ->
                    a.gravity = Gravity.TOP or Gravity.START
                    if (isAlignLeft) {
                        a.x = posX!!
                    } else {
                        val tmp = posX!! - LUIUtil.getWidthOfView(dialogView)
                        a.x = tmp
                    }
                    if (isAlignTop) {
                        val tmp = posY!! - LUIUtil.getHeightOfView(dialogView)
                        a.y = tmp
                    } else {
                        a.y = posY!!
                    }
                }
            }
        }
        return d
    }

    private fun init(v: View) {
        val btOK = v.findViewById(R.id.btOK) as Button
        val btCancel = v.findViewById(R.id.btCancel) as Button
        btOK.setOnClickListener { dismiss() }
        btCancel.setOnClickListener { dismiss() }
    }

    fun showImmersiveCenter(activity: Activity, sizeWidthPx: Int?, sizeHeightPx: Int?) {
        if (activity is BaseActivity) {
            activity.supportFragmentManager.let { fm ->
                show(fm, TAG)
                fm.executePendingTransactions()
                dialog?.window?.let { w ->
                    w.decorView.systemUiVisibility = activity.window.decorView.systemUiVisibility
                    w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                    if (sizeWidthPx != null && sizeHeightPx != null) {
                        w.setLayout(sizeWidthPx, sizeHeightPx)
                    }
                }
            }
        }
    }

    fun showImmersivePos(activity: Activity, posX: Int, posY: Int, sizeWidthPx: Int?, sizeHeightPx: Int?, isAlignLeft: Boolean, isAlignTop: Boolean) {
        //LLog.d(TAG, "showImmersive")
        this.posX = posX
        this.posY = posY
        this.isAlignLeft = isAlignLeft
        this.isAlignTop = isAlignTop
        if (activity is BaseActivity) {
            activity.supportFragmentManager.let { fm ->
                show(fm, TAG)
                fm.executePendingTransactions()
                dialog?.window?.let { w ->
                    w.decorView.systemUiVisibility = activity.window.decorView.systemUiVisibility
                    w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                    if (sizeWidthPx != null && sizeHeightPx != null) {
                        w.setLayout(sizeWidthPx, sizeHeightPx)
                    }
                }
            }
        }
    }
}