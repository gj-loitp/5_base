package com.core.helper.more

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.helper.adhelper.AdHelperActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LAppResource
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.l_frm_more.*

@LogTag("FrmMore")
class FrmMore : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_more, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupThemes()
    }

    private fun setupViews() {
        btRateApp.setOnClickListener(this)
        btMoreApp.setOnClickListener(this)
        btShareApp.setOnClickListener(this)
        btLikeFbFanpage.setOnClickListener(this)
        btSupport.setOnClickListener(this)
        btAdHelper.setOnClickListener(this)

        LUIUtil.setPullLikeIOSVertical(nestedScrollView)
    }

    private fun setupThemes() {
//        val isDarkTheme = LSharedPrefsUtil.instance.getBoolean(Constants.KEY_IS_DARK_THEME, true)
        val isDarkTheme = true
        if (isDarkTheme) {
            layoutRootViewMore.setBackgroundColor(Color.BLACK)

            ivWhySeeAd.setColorFilter(Color.WHITE)
            tvWhySeeAd.setTextColor(Color.WHITE)

            ivRate.setColorFilter(Color.WHITE)
            tvRate.setTextColor(Color.WHITE)

            ivMore.setColorFilter(Color.WHITE)
            tvMore.setTextColor(Color.WHITE)

            ivShare.setColorFilter(Color.WHITE)
            tvShare.setTextColor(Color.WHITE)

            ivLikeFbFanpage.setColorFilter(Color.WHITE)
            tvLikeFbFanpage.setTextColor(Color.WHITE)

            ivSupport.setColorFilter(Color.WHITE)
            tvSupport.setTextColor(Color.WHITE)
        } else {
            layoutRootViewMore.setBackgroundColor(LAppResource.getColor(R.color.whiteSmoke))

            ivWhySeeAd.setColorFilter(Color.BLACK)
            tvWhySeeAd.setTextColor(Color.BLACK)

            ivRate.setColorFilter(Color.BLACK)
            tvRate.setTextColor(Color.BLACK)

            ivMore.setColorFilter(Color.BLACK)
            tvMore.setTextColor(Color.BLACK)

            ivShare.setColorFilter(Color.BLACK)
            tvShare.setTextColor(Color.BLACK)

            ivLikeFbFanpage.setColorFilter(Color.BLACK)
            tvLikeFbFanpage.setTextColor(Color.BLACK)

            ivSupport.setColorFilter(Color.BLACK)
            tvSupport.setTextColor(Color.BLACK)
        }
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
                    startActivity(intent)
                    LActivityUtil.tranIn(it)
                }
            }
        }
    }
}