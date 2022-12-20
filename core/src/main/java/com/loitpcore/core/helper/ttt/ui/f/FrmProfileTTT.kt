package com.loitpcore.core.helper.ttt.ui.f

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.R
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.setSafeOnClickListener
import com.loitpcore.core.helper.ttt.ui.a.TTTComicActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LImageUtil
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import kotlinx.android.synthetic.main.l_frm_ttt_comic_profile.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("FrmProfileTTT")
class FrmProfileTTT : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_ttt_comic_profile
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
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
            val bottomSheetSettingTTTFragment = BottomSheetSettingTTTFragment()
            bottomSheetSettingTTTFragment.onSwitchTheme = {
                activity?.let {
                    val intent = Intent(it, TTTComicActivity::class.java)
                    startActivity(intent)
                    LActivityUtil.tranIn(context = it)
                    it.finish()//correct
                }
            }
            bottomSheetSettingTTTFragment.show(
                childFragmentManager,
                bottomSheetSettingTTTFragment.tag
            )
        }
        btInformation.setSafeOnClickListener {
            val bottomSheetInformationTTTFragment = BottomSheetInformationTTTFragment()
            bottomSheetInformationTTTFragment.show(
                childFragmentManager,
                bottomSheetInformationTTTFragment.tag
            )
        }
    }
}
