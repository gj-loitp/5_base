package com.loitp.core.helper.more

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.R
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.*
import com.loitp.core.helper.adHelper.AdHelperActivity
import kotlinx.android.synthetic.main.l_f_more.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("FrmMore")
class FrmMore : BaseFragment(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_f_more
    }

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

        nestedScrollView.setPullLikeIOSVertical()
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
                    startActivity(intent)
                    it.tranIn()
                }
            }
        }
    }
}
