package com.core.loitp.more

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView

import com.core.base.BaseFragment
import com.core.loitp.adhelper.AdHelperActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil

import loitp.core.R

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

class FrmMore : BaseFragment(), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.bt_rate_app).setOnClickListener(this)
        view.findViewById<View>(R.id.bt_more_app).setOnClickListener(this)
        view.findViewById<View>(R.id.bt_share_app).setOnClickListener(this)
        view.findViewById<View>(R.id.bt_like_fb_fanpage).setOnClickListener(this)
        view.findViewById<View>(R.id.bt_support).setOnClickListener(this)
        view.findViewById<View>(R.id.bt_ad_helper).setOnClickListener(this)

        val nestedScrollView = view.findViewById<View>(R.id.sv) as NestedScrollView
        LUIUtil.setPullLikeIOSVertical(nestedScrollView)
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_more
    }

    override fun onClick(v: View) {
        activity?.let {
            when (v.id) {
                R.id.bt_rate_app -> LSocialUtil.rateApp(it, it.packageName)
                R.id.bt_more_app -> LSocialUtil.moreApp(it)
                R.id.bt_share_app -> LSocialUtil.shareApp(it)
                R.id.bt_like_fb_fanpage -> LSocialUtil.likeFacebookFanpage(it)
                R.id.bt_support -> LSocialUtil.chatMessenger(it)
                R.id.bt_ad_helper -> {
                    val intent = Intent(it, AdHelperActivity::class.java)
                    startActivity(intent)
                    LActivityUtil.tranIn(it)
                }
            }
        }
    }
}