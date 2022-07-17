package com.loitpcore.core.helper.more

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.R
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFragment
import com.loitpcore.core.helper.adHelper.AdHelperActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.l_frm_more.*

@LogTag("FrmMore")
class FrmMore : BaseFragment(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_more
    }

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

        LUIUtil.setPullLikeIOSVertical(nestedScrollView)
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
