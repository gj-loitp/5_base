package com.views.dialog.iosdialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.widget.TextView

import loitp.core.R

class iOSDialog(context: Context) {
    private val TAG = javaClass.simpleName
    private val dialog: Dialog = Dialog(context)
    private var dialogButtonOk: TextView? = null
    private var dialogButtonNo: TextView? = null
    private var titleLbl: TextView? = null
    private var subtitleLbl: TextView? = null
    private var separator: View? = null
    private var negativeExist: Boolean = false

    init {
        dialog.setContentView(R.layout.dialog_ios_two_buttons)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initViews()
    }


    fun setPositiveListener(okListener: View.OnClickListener) {
        dialogButtonOk?.setOnClickListener(okListener)
    }

    fun setNegativeListener(okListener: View.OnClickListener) {
        if (!negativeExist) {
            Log.e(TAG, "!!! Negative button isn't visible, set it with setNegativeLabel()!!!")
        }
        dialogButtonNo?.setOnClickListener(okListener)
    }

    fun show() {
        if (!negativeExist) {
            dialogButtonNo?.visibility = View.GONE
            separator?.visibility = View.GONE
        }
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun setTitle(title: String) {
        titleLbl?.text = title
    }

    fun setSubtitle(subtitle: String) {
        subtitleLbl?.text = subtitle
    }

    fun setPositiveLabel(positive: String) {
        dialogButtonOk?.text = positive
    }

    fun setNegativeLabel(negative: String) {
        negativeExist = true
        dialogButtonNo?.text = negative
    }

    fun setBoldPositiveLabel(bold: Boolean) {
        if (bold) {
            dialogButtonOk?.setTypeface(null, Typeface.BOLD)
        } else {
            dialogButtonOk?.setTypeface(null, Typeface.NORMAL)
        }
    }

    fun setTipefaces(appleFont: Typeface) {
        titleLbl?.typeface = appleFont
        subtitleLbl?.typeface = appleFont
        dialogButtonOk?.typeface = appleFont
        dialogButtonNo?.typeface = appleFont
    }


    private fun initViews() {
        titleLbl = dialog.findViewById(R.id.title)
        subtitleLbl = dialog.findViewById(R.id.subtitle)
        dialogButtonOk = dialog.findViewById(R.id.dialogButtonOK)
        dialogButtonNo = dialog.findViewById(R.id.dialogButtonNO)
        separator = dialog.findViewById(R.id.separator)
    }
}
