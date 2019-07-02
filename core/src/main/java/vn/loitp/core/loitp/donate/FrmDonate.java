package vn.loitp.core.loitp.donate;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.animation.confetti.CommonConfetti;
import vn.loitp.views.animation.confetti.ConfettiManager;
import vn.loitp.views.textview.textdecorator.lib.TextDecorator;

public class FrmDonate extends BaseFragment {
    protected int goldDark, goldMed, gold, goldLight, colorPrimary;
    protected int[] colors;
    private final List<ConfettiManager> activeConfettiManagers = new ArrayList<>();
    private RelativeLayout rv;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ScrollView sv = view.findViewById(R.id.sv);
        LUIUtil.setPullLikeIOSVertical(sv);
        rv = view.findViewById(R.id.rv);
        final TextView tv = view.findViewById(R.id.tv);
        final String text = "Cuộc sống hôm nay tuy vất vả nhưng cuộc đời ơi ta mến thương và mọi người có thể ủng hộ mình qua tài khoản: \n" +
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
                "Những đóng góp của mọi người là động lực và trách nhiệm để mình có thể cho ra nhiều ứng dụng hay hơn nữa. Chân thành cảm ơn!";

        TextDecorator
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
                .build();
        final Resources res = getResources();
        goldDark = res.getColor(R.color.gold_dark);
        goldMed = res.getColor(R.color.gold_med);
        gold = res.getColor(R.color.gold);
        goldLight = res.getColor(R.color.gold_light);
        colorPrimary = res.getColor(R.color.colorPrimary);
        colors = new int[]{goldDark, goldMed, gold, goldLight, colorPrimary};
        LUIUtil.setDelay(500, mls -> activeConfettiManagers.add(generateOnce()));
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_donate;
    }

    protected ConfettiManager generateOnce() {
        return CommonConfetti.rainingConfetti(rv, colors).oneShot();
    }

    protected ConfettiManager generateStream() {
        return CommonConfetti.rainingConfetti(rv, colors).stream(3000);
    }

    protected ConfettiManager generateInfinite() {
        return CommonConfetti.rainingConfetti(rv, colors).infinite();
    }
}