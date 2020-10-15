package com.core.helper.mup.comic.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.common.Constants
import com.core.helper.adhelper.AdHelperActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LSharedPrefsUtil
import com.core.utilities.LSocialUtil
import com.views.textview.textdecorator.LTextDecorator
import kotlinx.android.synthetic.main.l_frm_girl_information.*

@LogTag("FrmInformation")
class FrmProfile : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_comic_profile, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    fun setupViews() {
        btRateApp.setOnClickListener(this)
        btMoreApp.setOnClickListener(this)
        btShareApp.setOnClickListener(this)
        btLikeFbFanpage.setOnClickListener(this)
        btSupport.setOnClickListener(this)
        btAdHelper.setOnClickListener(this)

        val isShowDonation = LSharedPrefsUtil.instance.getBoolean(Constants.COMIC_SHOW_DONATION)
        if (isShowDonation) {
            textView.visibility = View.VISIBLE
            val text = Constants.DONATION_INFOR_LOITP

            LTextDecorator
                    .decorate(textView, text)
                    .setTextColor(R.color.red,
                            "❤ Vietcombank", "0371000106443",
                            "❤ Techcombank", "19034585806016",
                            "❤ Viet Capital Bank - Ngân hàng TMCP Bản Việt", "8007041105519",
                            "❤ VPBank", "166210585",
                            "❤ Momo", "0764088864")
                    //.setBackgroundColor(R.color.colorPrimary, "dolor", "elit")
                    //.strikethrough("Duis", "Praesent")
                    .underline("Chân thành cảm ơn!")
                    //.setSubscript("vitae")
                    //.makeTextClickable(new OnTextClickListener() {
                    //    @Override
                    //    public void onClick(View view, String text) {
                    //
                    //    }
                    //}, false, "porta", "commodo", "tempor venenatis nulla")
                    //.setTextColor(android.R.color.holo_green_light, "porta", "commodo", "tempor venenatis nulla")
                    .build()
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
                    intent.putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, true)
                    startActivity(intent)
                    LActivityUtil.tranIn(it)
                }
            }
        }
    }

}
