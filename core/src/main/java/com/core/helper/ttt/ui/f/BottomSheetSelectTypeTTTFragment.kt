package com.core.helper.ttt.ui.f

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.R
import com.annotation.LogTag
import com.core.base.BaseBottomSheetFragment
import com.core.common.Constants
import com.core.helper.adhelper.AdHelperActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LSocialUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.l_bottom_sheet_ttt_select_type_fragment.*

@LogTag("BottomSheetInformationTTTFragment")
class BottomSheetSelectTypeTTTFragment : BaseBottomSheetFragment(
        layoutId = R.layout.l_bottom_sheet_ttt_select_type_fragment,
        height = WindowManager.LayoutParams.WRAP_CONTENT,
        isDraggable = true,
        firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btRateApp.setOnClickListener(this)
        btMoreApp.setOnClickListener(this)
        btShareApp.setOnClickListener(this)
        btLikeFbFanpage.setOnClickListener(this)
        btSupport.setOnClickListener(this)
        btAdHelper.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        activity?.let {
            when (v) {
                btRateApp -> LSocialUtil.rateApp(activity = it, packageName = it.packageName)
                btMoreApp -> LSocialUtil.moreApp(activity = it)
                btShareApp -> LSocialUtil.shareApp(activity = it)
                btLikeFbFanpage -> LSocialUtil.likeFacebookFanpage(activity = it)
                btSupport -> LSocialUtil.chatMessenger(activity = it)
                btAdHelper -> {
                    val intent = Intent(it, AdHelperActivity::class.java)
                    intent.putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, false)
                    startActivity(intent)
                    LActivityUtil.tranIn(it)
                }
            }
        }
    }
}
