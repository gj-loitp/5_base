package vn.loitp.a.customView.bs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseBottomSheetFragment
import com.loitp.core.common.Constants
import com.loitp.core.helper.adHelper.AdHelperActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.layout_bottom_sheet_sample.*
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
        layoutId = R.layout.layout_bottom_sheet_sample,
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
                btShowDialogProgress -> {
                    showDialogProgress()
                    LUIUtil.setDelay(2000) {
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