package com.views.dialog.imersivedialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.core.base.BaseActivity
import com.core.utilities.LLog
import com.views.LToast

/**
 * Created by Loitp on 3/30/2018.
 */

class ImmersiveDialogFragment : DialogFragment() {
    private val TAG = javaClass.simpleName

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alertDialog = AlertDialog.Builder(activity)
                .setTitle("Example Dialog")
                .setMessage("Some text.")
                .create()

        // Temporarily set the dialogs window to not focusable to prevent the short
        // popup of the navigation bar.
        alertDialog.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            context?.let { c ->
                LToast.show(c, "Touch OK")
            }
        }
        //int color = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        //alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color);
        return alertDialog
    }

    fun showImmersive(activity: Activity) {
        if (activity is BaseActivity) {
            activity.supportFragmentManager.let { fm ->
                show(fm, TAG)
                LLog.d(TAG, "show")

                // It is necessary to call executePendingTransactions() on the FragmentManager
                // before hiding the navigation bar, because otherwise getWindow() would raise a
                // NullPointerException since the window was not yet created.
                fm.executePendingTransactions()

                // Hide the navigation bar. It is important to do this after show() was called.
                // If we would do this in onCreateDialog(), we would get a requestFeature()
                // error.
                dialog?.let { d ->
                    d.window?.decorView?.systemUiVisibility = activity.window.decorView.systemUiVisibility

                    // Make the dialogs window focusable again.
                    d.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                }
            }
        }
    }
}