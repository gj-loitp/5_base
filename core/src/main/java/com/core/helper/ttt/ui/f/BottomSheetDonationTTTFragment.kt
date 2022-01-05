package com.core.helper.ttt.ui.f

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.LogTag
import com.core.base.BaseBottomSheetFragment
import com.core.common.Constants
import com.core.utilities.LScreenUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.views.textview.textdecorator.LTextDecorator
import kotlinx.android.synthetic.main.l_bottom_sheet_ttt_donation_fragment.*

@LogTag("BottomSheetDonationTTTFragment")
class BottomSheetDonationTTTFragment : BaseBottomSheetFragment(
    layoutId = R.layout.l_bottom_sheet_ttt_donation_fragment,
    height = LScreenUtil.screenHeight - LScreenUtil.getStatusBarHeight(),
    isDraggable = true,
    firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val text = Constants.DONATION_INFOR_LOITP

        LTextDecorator
            .decorate(textView, text)
            .setTextColor(
                R.color.red,
                "❤ Vietcombank", "0371000106443",
                "❤ Techcombank", "19034585806016",
                "❤ Viet Capital Bank - Ngân hàng TMCP Bản Việt", "8007041105519",
                "❤ VPBank", "166210585",
                "❤ Momo", "0764088864"
            )
            // .setBackgroundColor(R.color.colorPrimary, "dolor", "elit")
            // .strikethrough("Duis", "Praesent")
            .underline("Chân thành cảm ơn!")
            // .setSubscript("vitae")
            // .makeTextClickable(new OnTextClickListener() {
            //    @Override
            //    public void onClick(View view, String text) {
            //
            //    }
            // }, false, "porta", "commodo", "tempor venenatis nulla")
            // .setTextColor(android.R.color.holo_green_light, "porta", "commodo", "tempor venenatis nulla")
            .build()

//        onStateChanged = { _, newState ->
//            logD("onStateChanged newState $newState")
//        }
//        onSlide = { _, slideOffset ->
//            logD("onSlide slideOffset $slideOffset")
//        }
    }
}
