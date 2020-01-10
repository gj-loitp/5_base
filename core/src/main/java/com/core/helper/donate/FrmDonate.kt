package com.core.helper.donate

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.R
import com.core.base.BaseFragment
import com.core.utilities.LUIUtil
import com.views.animation.confetti.CommonConfetti
import com.views.animation.confetti.ConfettiManager
import com.views.textview.textdecorator.LTextDecorator
import kotlinx.android.synthetic.main.l_frm_donate.*
import java.util.*

class FrmDonate : BaseFragment() {
    override fun setTag(): String? {
        return javaClass.simpleName
    }

    private var goldDark: Int = 0
    private var goldMed: Int = 0
    private var gold: Int = 0
    private var goldLight: Int = 0
    private var colorPrimary: Int = 0
    private var colors: IntArray? = null
    private val activeConfettiManagers = ArrayList<ConfettiManager>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                .decorate(tv, text)
                .setTextColor(R.color.colorPrimary, "❤", "Trần Phú Lợi", "0371000106443", "166210585", "164205468", "0764088864")
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

        context?.let {
            goldDark = ContextCompat.getColor(it, R.color.gold_dark)
            goldMed = ContextCompat.getColor(it, R.color.gold_med)
            gold = ContextCompat.getColor(it, R.color.gold)
            goldLight = ContextCompat.getColor(it, R.color.gold_light)
            colorPrimary = ContextCompat.getColor(it, R.color.colorPrimary)
            colors = intArrayOf(goldDark, goldMed, gold, goldLight, colorPrimary)
        }

        LUIUtil.setDelay(mls = 500, runnable = Runnable {
            activeConfettiManagers.add(generateOnce())
        })
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_donate
    }

    private fun generateOnce(): ConfettiManager {
        return CommonConfetti.rainingConfetti(rv, colors).oneShot()
    }

    private fun generateStream(): ConfettiManager {
        return CommonConfetti.rainingConfetti(rv, colors).stream(3000)
    }

    private fun generateInfinite(): ConfettiManager {
        return CommonConfetti.rainingConfetti(rv, colors).infinite()
    }
}
