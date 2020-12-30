package com.core.base

import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.R
import com.core.utilities.LUIUtil.Companion.allowInfiniteLines
import com.core.utilities.LUIUtil.Companion.withBackground
import com.google.android.material.snackbar.Snackbar

open class BaseDialogFragment : DialogFragment() {

    fun <T : ViewModel> getViewModel(className: Class<T>): T? {

        return activity?.let { ViewModelProvider(it).get(className) }
    }

    fun <T : ViewModel> getSelfViewModel(className: Class<T>): T {

        return ViewModelProvider(this).get(className)
    }

    fun lockScreen(isLock: Boolean) {
        if (isLock) {
            activity?.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

            activity?.window?.decorView?.alpha = 0.5f
        } else {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            activity?.window?.decorView?.alpha = 1.0f
        }
    }

    fun logD(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun logE(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    /**
     * fix bug: Can not perform this action after onSaveInstanceState
     * https://medium.com/@waseefakhtar/demystifying-androids-fragmenttransaction-and-solving-can-not-perform-this-action-after-3d45004aa22f
     */
    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showSnackBarInfor(msg: String, view: View? = dialog?.window?.findViewById(android.R.id.content)) {
        view?.let { v ->
            val snackBar = Snackbar
                    .make(v, msg, Snackbar.LENGTH_LONG)
                    .withBackground(R.drawable.bg_toast_infor)
                    .allowInfiniteLines()
            snackBar.show()
        }
    }

    fun showSnackBarWarning(msg: String, view: View? = dialog?.window?.findViewById(android.R.id.content)) {
        view?.let { v ->
            val snackBar = Snackbar
                    .make(v, msg, Snackbar.LENGTH_LONG)
                    .withBackground(R.drawable.bg_toast_warning)
                    .allowInfiniteLines()
            snackBar.show()
        }
    }

    fun showSnackBarError(msg: String, view: View? = dialog?.window?.findViewById(android.R.id.content)) {
        view?.let { v ->
            val snackBar = Snackbar
                    .make(v, msg, Snackbar.LENGTH_LONG)
                    .withBackground(R.drawable.bg_toast_err)
                    .allowInfiniteLines()
            snackBar.show()
        }
    }
}
