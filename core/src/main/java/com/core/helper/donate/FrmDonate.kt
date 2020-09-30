package com.core.helper.donate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
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
    }

    private fun setupViews() {
        val text = """Cuộc sống hôm nay tuy vất vả nhưng cuộc đời ơi ta mến thương và mọi người có thể ủng hộ mình qua tài khoản: 

❤ Vietcombank
Tên tài khoản: TRAN PHU LOI
Số tài khoản: 0371000106443
Ngân hàng Vietcombank, Chi nhánh Tân Định, Q1, TPHCM.

❤ Techcombank
Tên tài khoản: TRAN PHU LOI
Số tài khoản: 19034585806016

❤ Viet Capital Bank - Ngân hàng TMCP Bản Việt
Tên tài khoản: TRAN PHU LOI
Số tài khoản: 8007041105519
Chi nhánh: Hồ Chí Minh
Tỉnh/ Thành phố: Hồ Chí Minh

❤ VPBank
Tên tài khoản: TRAN PHU LOI
Số tài khoản: 166210585
Ngân hàng VPBank, chi nhánh Bà Chiểu, Bình Thạnh, TPHCM.

❤ Momo:
Tên tài khoản: TRAN PHU LOI
Số điện thoại: 0764088864


Những đóng góp của mọi người là động lực và trách nhiệm để mình có thể cho ra nhiều ứng dụng hay hơn nữa. Chân thành cảm ơn!"""

        LTextDecorator
                .decorate(textView, text)
                .setTextColor(R.color.red,
                        "❤ Vietcombank", "0371000106443",
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
