package com.loitp.core.helper.ttt.ui.f

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.loitp.R
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseBottomSheetFragment
import com.loitp.core.common.AD_HELPER_IS_ENGLISH_LANGUAGE
import com.loitp.core.ext.*
import com.loitp.core.helper.adHelper.AdHelperActivity
import kotlinx.android.synthetic.main.l_f_ttt_information.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("BottomSheetInformationTTTFragment")
class BottomSheetInformationTTTFragment :
    BaseBottomSheetFragment(
        layoutId = R.layout.l_f_ttt_information,
        height = WindowManager.LayoutParams.WRAP_CONTENT,
        isDraggable = true,
        firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
    ),
    View.OnClickListener {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
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
                btRateApp -> it.rateApp(packageName = it.packageName)
                btMoreApp -> it.moreApp()
                btShareApp -> it.shareApp()
                btLikeFbFanpage -> it.likeFacebookFanpage()
                btSupport -> it.chatMessenger()
                btAdHelper -> {
                    val intent = Intent(it, AdHelperActivity::class.java)
                    intent.putExtra(AD_HELPER_IS_ENGLISH_LANGUAGE, false)
                    startActivity(intent)
                    it.tranIn()
                }
            }
        }
    }
}
