package com.core.helper.mup.comic.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.core.common.Constants
import com.core.utilities.LScreenUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.views.textview.textdecorator.LTextDecorator
import kotlinx.android.synthetic.main.l_frm_girl_information.*

class BottomSheetDonationFragment : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sheetDialog = BottomSheetDialog(requireContext(), theme)
        sheetDialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { layout ->
                val behaviour = BottomSheetBehavior.from(layout)
                setupFullHeight(layout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.isDraggable = false
//                behaviour.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                    override fun onStateChanged(bottomSheet: View, newState: Int) {
//                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
//                            behaviour.state = BottomSheetBehavior.STATE_HIDDEN
//                        }
//                    }
//
//                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
//                })
            }
        }
        return sheetDialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = LScreenUtil.screenHeight - LScreenUtil.getStatusBarHeight()
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.l_bottom_sheet_donation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val text = Constants.DONATION_INFOR_LOITP

        LTextDecorator
                .decorate(textView, text)
                .setTextColor(R.color.red,
                        "❤ Vietcombank", "0371000106443",
                        "❤ Techcombank", "19034585806016",
                        "❤ Viet Capital Bank - Ngân hàng TMCP Bản Việt", "8007041105519",
                        "❤ VPBank", "166210585",
                        "❤ Momo", "0764088864")
                //.setBackgroundColor(R.color.colorPrimary, "dolor", "elit")
                //.strikethrough("Duis", "Praesent")
                .underline("Chân thành cảm ơn!")
                //.setSubscript("vitae")
                //.makeTextClickable(new OnTextClickListener() {
                //    @Override
                //    public void onClick(View view, String text) {
                //
                //    }
                //}, false, "porta", "commodo", "tempor venenatis nulla")
                //.setTextColor(android.R.color.holo_green_light, "porta", "commodo", "tempor venenatis nulla")
                .build()
    }
}
