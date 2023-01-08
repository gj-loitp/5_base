package vn.loitp.a.cv.bs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseBottomSheetFragment
import com.loitp.core.common.Constants
import com.loitp.core.ext.*
import com.loitp.core.helper.adHelper.AdHelperActivity
import kotlinx.android.synthetic.main.l_bottom_sheet_sample.*
import vn.loitp.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("SampleBaseBottomSheet")
class SampleBaseBottomSheet :
    BaseBottomSheetFragment(
        layoutId = R.layout.l_bottom_sheet_sample,
        height = WindowManager.LayoutParams.WRAP_CONTENT,
        isDraggable = true,
        firstBehaviourState = BottomSheetBehavior.STATE_EXPANDED
    ),
    View.OnClickListener {

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
        btShowDialogProgress.setOnClickListener(this)
        btHideDialogProgress.setOnClickListener(this)
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
                    intent.putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, false)
                    startActivity(intent)
                    it.tranIn()
                }
                btShowDialogProgress -> {
                    showDialogProgress()
                    setDelay(2000) {
                        hideDialogProgress()
                    }
                }
                btHideDialogProgress -> {
                    hideDialogProgress()
                }
            }
        }
//        dismiss()
    }
}