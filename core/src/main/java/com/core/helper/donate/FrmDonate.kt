package com.core.helper.donate

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.common.Constants
import com.core.utilities.LAppResource
import com.core.utilities.LSharedPrefsUtil
import com.views.textview.textdecorator.LTextDecorator
import kotlinx.android.synthetic.main.l_frm_donate.*

@LogTag("FrmDonate")
class FrmDonate : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_donate, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupTheme()
    }

    private fun setupViews() {
        val text = Constants.DONATION_INFOR_LOITP

        LTextDecorator
                .decorate(textView = textView, content = text)
                .setTextColor(R.color.red,
                        "❤ Vietcombank", "0371000106443",
                        "❤ Techcombank", "19034585806016",
                        "❤ Viet Capital Bank - Ngân hàng TMCP Bản Việt", "8007041105519",
                        "❤ VPBank", "166210585",
                        "❤ Momo", "0764088864")
                .underline("Chân thành cảm ơn!")
                .build()
    }

    private fun setupTheme() {
        val isDarkTheme = LSharedPrefsUtil.instance.getBoolean(Constants.KEY_IS_DARK_THEME, true)
        if (isDarkTheme) {
            layoutRootViewDonate.setBackgroundColor(Color.BLACK)
            textView.setTextColor(Color.WHITE)
        } else {
            layoutRootViewDonate.setBackgroundColor(LAppResource.getColor(R.color.whiteSmoke))
            textView.setTextColor(Color.BLACK)
        }
    }
}
