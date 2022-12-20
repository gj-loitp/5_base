package com.loitpcore.views.bottomSheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.loitpcore.R
import com.loitp.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.l_frm_bottom_sheet_dialog_option.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class BottomSheetOptionFragment(
    private val isCancelableFragment: Boolean = true,
    private val isShowIvClose: Boolean = true,
    private val title: String,
    private val message: String,
    private val textButton1: String? = null,
    private val textButton2: String? = null,
    private val textButton3: String? = null,
    private val onClickButton1: ((Unit) -> Unit)? = null,
    private val onClickButton2: ((Unit) -> Unit)? = null,
    private val onClickButton3: ((Unit) -> Unit)? = null,
    private val onDismiss: ((Unit) -> Unit)? = null
) : BottomSheetDialogFragment() {

    private var onDismissNotify = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = isCancelableFragment
        return inflater.inflate(R.layout.l_frm_bottom_sheet_dialog_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // https://stackoverflow.com/questions/37104960/bottomsheetdialog-with-transparent-background
        dialog?.apply {
            setOnShowListener {
                val bottomSheet = findViewById<View?>(R.id.design_bottom_sheet)
                bottomSheet?.setBackgroundResource(android.R.color.transparent)
            }
        }

        setupViews()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        if (onDismissNotify) {
            onDismiss?.invoke(Unit)
        }
    }

    private fun setupViews() {
        if (isShowIvClose) {
            ivClose.visibility = View.VISIBLE
        } else {
            ivClose.visibility = View.GONE
        }
        tvTitle.text = title
        tvMsg.text = message
        if (textButton1.isNullOrEmpty()) {
            bt1.visibility = View.GONE
        } else {
            bt1.visibility = View.VISIBLE
            bt1.text = textButton1
        }
        if (textButton2.isNullOrEmpty()) {
            bt2.visibility = View.GONE
        } else {
            bt2.visibility = View.VISIBLE
            bt2.text = textButton2
        }
        if (textButton3.isNullOrEmpty()) {
            bt3.visibility = View.GONE
        } else {
            bt3.visibility = View.VISIBLE
            bt3.text = textButton3
        }

        var countBtVisibile = 0
        if (bt1.visibility == View.VISIBLE) {
            countBtVisibile++
        }
        if (bt2.visibility == View.VISIBLE) {
            countBtVisibile++
        }
        if (bt3.visibility == View.VISIBLE) {
            countBtVisibile++
        }
        when (countBtVisibile) {
            1 -> {
                viewSpace1.visibility = View.GONE
                viewSpace2.visibility = View.GONE
            }
            2 -> {
                viewSpace1.visibility = View.GONE
                viewSpace2.visibility = View.VISIBLE
            }
            3 -> {
                viewSpace1.visibility = View.VISIBLE
                viewSpace2.visibility = View.VISIBLE
            }
        }

        if (isCancelableFragment) {
            layoutRootView.setSafeOnClickListener {
                dismiss()
            }
        }
        LUIUtil.setSafeOnClickListenerElastic(
            view = ivClose,
            runnable = {
                dismiss()
            }
        )
        bt1.setSafeOnClickListener {
            onDismissNotify = false
            onClickButton1?.invoke(Unit)
            dismiss()
        }
        bt2.setSafeOnClickListener {
            onDismissNotify = false
            onClickButton2?.invoke(Unit)
            dismiss()
        }
        bt3.setSafeOnClickListener {
            onDismissNotify = false
            onClickButton3?.invoke(Unit)
            dismiss()
        }
    }
}
