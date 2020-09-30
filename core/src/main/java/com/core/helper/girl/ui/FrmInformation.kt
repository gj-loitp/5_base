package com.core.helper.girl.ui

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
import com.core.utilities.LSocialUtil
import com.views.textview.textdecorator.LTextDecorator
import kotlinx.android.synthetic.main.l_frm_girl_information.*

@LogTag("FrmInformation")
class FrmInformation : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_girl_information, container, false)
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

        val text = "Cuộc sống hôm nay tuy vất vả nhưng cuộc đời ơi ta mến thương và mọi người có thể ủng hộ mình qua tài khoản: \n" +
                "\n" +
                "❤ Vietcombank\n" +
                "Tên tài khoản: Trần Phú Lợi\n" +
                "Số tài khoản: 0371000106443\n" +
                "Ngân hàng Vietcombank, Chi nhánh Tân Định, Q1, TPHCM.\n\n" +
                "❤ VPBank\n" +
                "Tên tài khoản: Trần Phú Lợi\n" +
                "Số tài khoản: 166210585\n" +
                "Ngân hàng VPBank, chi nhánh Bà Chiểu, Bình Thạnh, TPHCM.\n\n" +
                "❤ Timo\n" +
                "Tên tài khoản: Trần Phú Lợi\n" +
                "Số tài khoản: 164205468\n\n" +
                "❤ Momo:\n" +
                "Số điện thoại 0764088864\n" +
                "\n\n" +
                "Những đóng góp của mọi người là động lực và trách nhiệm để mình có thể cho ra nhiều ứng dụng hay hơn nữa. Chân thành cảm ơn!"

        LTextDecorator
                .decorate(textView, text)
                .setTextColor(R.color.red, "❤", "Trần Phú Lợi", "0371000106443", "166210585", "164205468", "0764088864")
                .underline("Chân thành cảm ơn!")
                .build()
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
                    intent.putExtra(Constants.IS_DARK_THEME, true)
                    startActivity(intent)
                    LActivityUtil.tranIn(it)
                }
            }
        }
    }

}
