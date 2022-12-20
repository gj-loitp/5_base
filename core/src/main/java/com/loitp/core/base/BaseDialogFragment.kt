package com.loitp.core.base

import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.loitpcore.R
import com.loitpcore.core.utilities.LUIUtil.Companion.allowInfiniteLines
import com.loitpcore.core.utilities.LUIUtil.Companion.withBackground
import timber.log.Timber

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
open class BaseDialogFragment : DialogFragment() {

    fun <T : ViewModel> getViewModel(className: Class<T>): T? {

        return activity?.let { ViewModelProvider(it)[className] }
    }

    @Suppress("unused")
    fun <T : ViewModel> getSelfViewModel(className: Class<T>): T {

        return ViewModelProvider(this)[className]
    }

    @Suppress("unused")
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

    fun logD(
        tag: String,
        msg: String
    ) {
        Timber.tag(tag).d(msg)
    }

    fun logE(
        tag: String,
        msg: String
    ) {
        Timber.tag(tag).e(msg)
    }

    /**
     * fix bug: Can not perform this action after onSaveInstanceState
     * https://medium.com/@waseefakhtar/demystifying-androids-fragmenttransaction-and-solving-can-not-perform-this-action-after-3d45004aa22f
     */
    override fun show(
        manager: FragmentManager,
        tag: String?
    ) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Suppress("unused")
    fun showSnackBarInformation(
        msg: String,
        view: View? = dialog?.window?.findViewById(android.R.id.content),
        isFullWidth: Boolean = false
    ) {
        view?.let { v ->
            val snackBar = Snackbar
                .make(v, msg, Snackbar.LENGTH_LONG)
                .withBackground(R.drawable.bg_toast_infor)
                .allowInfiniteLines()
            if (isFullWidth) {
                snackBar.view.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            snackBar.show()
        }
    }

    @Suppress("unused")
    fun showSnackBarWarning(
        msg: String,
        view: View? = dialog?.window?.findViewById(android.R.id.content),
        isFullWidth: Boolean = false
    ) {
        view?.let { v ->
            val snackBar = Snackbar
                .make(v, msg, Snackbar.LENGTH_LONG)
                .withBackground(R.drawable.bg_toast_warning)
                .allowInfiniteLines()
            if (isFullWidth) {
                snackBar.view.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            snackBar.show()
        }
    }

    @Suppress("unused")
    fun showSnackBarError(
        msg: String,
        view: View? = dialog?.window?.findViewById(android.R.id.content),
        isFullWidth: Boolean = false
    ) {
        view?.let { v ->
            val snackBar = Snackbar
                .make(v, msg, Snackbar.LENGTH_LONG)
                .withBackground(R.drawable.bg_toast_err)
                .allowInfiniteLines()
            if (isFullWidth) {
                snackBar.view.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            snackBar.show()
        }
    }

    fun showDialogProgress() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showDialogProgress()
        }
    }

    fun hideDialogProgress() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideDialogProgress()
        }
    }
}
