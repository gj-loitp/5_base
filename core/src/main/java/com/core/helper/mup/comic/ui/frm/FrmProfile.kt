package com.core.helper.mup.comic.ui.frm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.common.Constants
import com.core.utilities.LImageUtil
import com.core.utilities.LSharedPrefsUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_frm_comic_profile.*

@LogTag("FrmInformation")
class FrmProfile : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_comic_profile, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    fun setupViews() {
        LImageUtil.load(context = activity, url = "https://live.staticflickr.com/336/31740727004_7a66635d62_b.jpg", imageView = ivBackground)
        LImageUtil.loadCircle(url = "https://live.staticflickr.com/8051/28816266454_a7d83db3b2_n.jpg", imageView = ivAvatar)
        tvUserName.text = getString(R.string.app_name_comic)

        btSetting.setSafeOnClickListener {
            val bottomSheetSettingFragment = BottomSheetSettingFragment()
            bottomSheetSettingFragment.show(childFragmentManager, bottomSheetSettingFragment.tag)
        }
        btInformation.setSafeOnClickListener {
            val bottomSheetInformationFragment = BottomSheetInformationFragment()
            bottomSheetInformationFragment.show(childFragmentManager, bottomSheetInformationFragment.tag)
        }

        val isShowDonation = LSharedPrefsUtil.instance.getBoolean(Constants.COMIC_SHOW_DONATION)
        if (isShowDonation) {
            btDonation.visibility = View.VISIBLE
            btDonation.setSafeOnClickListener {
                val bottomSheetDonationFragment = BottomSheetDonationFragment()
                bottomSheetDonationFragment.show(childFragmentManager, bottomSheetDonationFragment.tag)
            }
        } else {
            btDonation.visibility = View.GONE
        }
    }


}
