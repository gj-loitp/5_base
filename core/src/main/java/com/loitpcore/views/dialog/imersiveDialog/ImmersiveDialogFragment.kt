package com.loitpcore.views.dialog.imersiveDialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import com.loitpcore.core.base.BaseActivity
import com.loitpcore.core.base.BaseDialogFragment
import com.loitpcore.views.toast.LToast

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ImmersiveDialogFragment : BaseDialogFragment() {
    private val logTag = javaClass.simpleName

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alertDialog = AlertDialog.Builder(activity)
            .setTitle("Example Dialog")
            .setMessage("Some text.")
            .create()

        // Temporarily set the dialogs window to not focusable to prevent the short
        // popup of the navigation bar.
        alertDialog.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            LToast.show("Touch OK")
        }
        return alertDialog
    }

    fun showImmersive(activity: Activity) {
        if (activity is BaseActivity) {
            activity.supportFragmentManager.let { fm ->
                show(fm, logTag)

                // It is necessary to call executePendingTransactions() on the FragmentManager
                // before hiding the navigation bar, because otherwise getWindow() would raise a
                // NullPointerException since the window was not yet created.
                fm.executePendingTransactions()

                // Hide the navigation bar. It is important to do this after show() was called.
                // If we would do this in onCreateDialog(), we would get a requestFeature()
                // error.
                dialog?.let { d ->
                    d.window?.decorView?.systemUiVisibility =
                        activity.window.decorView.systemUiVisibility

                    // Make the dialogs window focusable again.
                    d.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                }
            }
        }
    }
}
