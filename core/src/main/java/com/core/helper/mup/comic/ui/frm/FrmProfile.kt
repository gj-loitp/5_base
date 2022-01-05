package com.core.helper.mup.comic.ui.frm

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.common.Constants
import com.core.helper.mup.comic.ui.activity.ComicActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LImageUtil
import com.core.utilities.LSharedPrefsUtil
import com.views.setSafeOnClickListener
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import kotlinx.android.synthetic.main.l_frm_mup_comic_profile.*

@LogTag("FrmInformation")
class FrmProfile : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_mup_comic_profile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    fun setupViews() {
        LImageUtil.load(
            context = activity,
            any = "https://live.staticflickr.com/336/31740727004_7a66635d62_b.jpg",
            imageView = ivBackground
        )
        LImageUtil.load(
            context = activity,
            any = "https://live.staticflickr.com/8051/28816266454_a7d83db3b2_n.jpg",
            imageView = ivAvatar,
            transformation = CropCircleWithBorderTransformation()
        )

        tvUserName.text = getString(R.string.app_name_comic)

        btSetting.setSafeOnClickListener {
            val bottomSheetSettingFragment = BottomSheetSettingFragment()
            bottomSheetSettingFragment.onSwitchTheme = {
                activity?.let {
                    val intent = Intent(it, ComicActivity::class.java)
                    startActivity(intent)
                    LActivityUtil.tranIn(context = it)
                    it.finish()
                }
            }
            bottomSheetSettingFragment.show(childFragmentManager, bottomSheetSettingFragment.tag)
        }
        btInformation.setSafeOnClickListener {
            val bottomSheetInformationFragment = BottomSheetInformationFragment()
            bottomSheetInformationFragment.show(
                childFragmentManager,
                bottomSheetInformationFragment.tag
            )
        }

        val isShowDonation = LSharedPrefsUtil.instance.getBoolean(Constants.COMIC_SHOW_DONATION)
        if (isShowDonation) {
            btDonation.visibility = View.VISIBLE
            btDonation.setSafeOnClickListener {
                val bottomSheetDonationFragment = BottomSheetDonationFragment()
                bottomSheetDonationFragment.show(
                    childFragmentManager,
                    bottomSheetDonationFragment.tag
                )
            }
        } else {
            btDonation.visibility = View.GONE
        }
    }
}
