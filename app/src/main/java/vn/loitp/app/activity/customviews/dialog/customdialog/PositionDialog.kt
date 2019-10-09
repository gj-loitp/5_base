package vn.loitp.app.activity.customviews.dialog.customdialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.core.base.BaseActivity
import com.core.utilities.LLog
import loitp.basemaster.R

class PositionDialog : DialogFragment() {
    private val TAG = javaClass.simpleName

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        LLog.d(TAG, "onCreateDialog")
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_position, null)
        dialogBuilder.setView(dialogView)
        isCancelable = true
        init(dialogView)
        val d = dialogBuilder.create()
        d.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        return d
    }

    private fun init(v: View) {
        val btOK = v.findViewById(R.id.btOK) as Button
        val btCancel = v.findViewById(R.id.btCancel) as Button
        btOK.setOnClickListener { dismiss() }
        btCancel.setOnClickListener { dismiss() }
    }

    fun showImmersive(activity: Activity) {
        LLog.d(TAG, "showImmersive")
        if (activity is BaseActivity) {
            activity.supportFragmentManager.let { fm ->
                show(fm, TAG)
                fm.executePendingTransactions()
                dialog?.window?.let { w ->
                    w.decorView.systemUiVisibility = activity.window.decorView.systemUiVisibility
                    w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                }
            }
        }
    }
}