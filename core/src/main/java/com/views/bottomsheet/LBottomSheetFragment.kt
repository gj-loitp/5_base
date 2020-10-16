package com.views.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.annotation.LogTag
import com.core.utilities.LLog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class LBottomSheetFragment(
        private val layoutId: Int,
        private val height: Int = WindowManager.LayoutParams.MATCH_PARENT,
        private val isDraggable: Boolean = true,
        private val firstBehaviourState: Int = BottomSheetBehavior.STATE_EXPANDED
) : BottomSheetDialogFragment() {
    protected var logTag: String? = null
    var onStateChanged: ((bottomSheet: View, newState: Int) -> Unit)? = null
    var onSlide: ((bottomSheet: View, slideOffset: Float) -> Unit)? = null

    protected fun <T : ViewModel> getViewModel(className: Class<T>): T {
        return ViewModelProvider(this).get(className)
    }

    protected fun logD(msg: String) {
        logTag?.let {
            LLog.d(it, msg)
        }
    }

    protected fun logE(msg: String) {
        logTag?.let {
            LLog.e(it, msg)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val tmpLogTag = javaClass.getAnnotation(LogTag::class.java)
        logTag = "logTag" + tmpLogTag?.value

        val sheetDialog = BottomSheetDialog(requireContext(), theme)
        sheetDialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { layout ->
                val behaviour = BottomSheetBehavior.from(layout)
                setupFullHeight(layout)
                behaviour.state = firstBehaviourState
                behaviour.isDraggable = isDraggable

                behaviour.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        onStateChanged?.invoke(bottomSheet, newState)
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        onSlide?.invoke(bottomSheet, slideOffset)
                    }
                })
            }
        }
        return sheetDialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
//        layoutParams.height = LScreenUtil.screenHeight - LScreenUtil.getStatusBarHeight()
        layoutParams.height = height
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }
}
