package vn.puresolutions.livestar.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.RelatedPackageAdapter;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.VipPackage;
import vn.puresolutions.livestar.core.api.model.VipPackageDetails;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * @author hoangphu
 * @since 11/29/16
 */

public class VipBenefitDialog extends Dialog implements RelatedPackageAdapter.OnBuyPackageListener {

    public interface OnBuyPackageListener {
        void onBuyPackage(VipPackage vipPackage);
    }

    @BindView(R.id.dialogVipBenefit_tvTitle)
    TextView tvTitle;
    @BindView(R.id.dialogVipBenefit_tvChatCharacters)
    TextView tvChatCharacters;
    @BindView(R.id.dialogVipBenefit_tvScreenText)
    TextView tvScreenText;
    @BindView(R.id.dialogVipBenefit_tvScreenTextAffect)
    TextView tvScreenTextEffect;
    @BindView(R.id.dialogVipBenefit_tvKick)
    TextView tvKick;
    @BindView(R.id.dialogVipBenefit_tvKey)
    TextView tvKey;
    @BindView(R.id.dialogVipBenefit_tvFrame)
    TextView tvFame;
    @BindView(R.id.dialogVipBenefit_imgVip)
    SimpleDraweeView imgVip;
    @BindView(R.id.dialogVipBenefit_imgPre)
    ImageView imgPre;
    @BindView(R.id.dialogVipBenefit_imgNext)
    ImageView imgNext;
    @BindView(R.id.dialogVipBenefit_rclPackages)
    RecyclerView rclPackages;

    private OnBuyPackageListener listener;
    private RelatedPackageAdapter adapter;
    private int index;
    private List<VipPackage> groupedPackage;
    private HashMap<Long, List<VipPackage>> packages;
    private HashMap<Long, VipPackageDetails> details;

    public VipBenefitDialog(Context context) {
        super(context, R.style.full_screen_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_vip_benefit);
        ButterKnife.bind(this);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

        adapter = new RelatedPackageAdapter();
        adapter.setListener(this);

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rclPackages.setLayoutManager(layout);
        rclPackages.setAdapter(adapter);
    }

    @Override
    public void setTitle(int titleId) {
        tvTitle.setText(getContext().getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    @OnClick(R.id.dialogVipBenefit_imgClose)
    void onCloseButtonClick() {
        dismiss();
    }

    @OnClick(R.id.dialogVipBenefit_imgNext)
    void onNextButtonClick() {
        if (index < packages.size() - 1) {
            index++;
            fillData();
        }
    }

    @OnClick(R.id.dialogVipBenefit_imgPre)
    void onPreButtonClick() {
        if (index > 0) {
            index--;
            fillData();
        }
    }

    public void setListener(OnBuyPackageListener listener) {
        this.listener = listener;
    }

    public void setData(HashMap<Long, List<VipPackage>> packages, HashMap<Long, VipPackageDetails> details, List<VipPackage> groupedPackage) {
        this.details = details;
        this.packages = packages;
        this.groupedPackage = groupedPackage;
        index = 0;
    }

    @Override
    public void show() {
        super.show();
        index = 0;
        fillData();
    }

    private void fillData() {
        if (packages != null && packages.size() > 0) {
            VipPackage vipPackage = groupedPackage.get(index);
            adapter.setItemsNtf(packages.get(vipPackage.getId()));
            VipPackageDetails detail = details.get(vipPackage.getId());
            if (UserInfo.getUserLoggedIn().isMBFUser()) {
                tvTitle.setText(vipPackage.getCode());
            } else {
                String name = detail.getName() + " - " + vipPackage.getDay();
                tvTitle.setText(name);
            }
            LImageUtils.loadImage(imgVip, detail.getImage());

            String characters = String.format(getContext().getString(R.string.chat_number), detail.getCharacterNumber());
            tvChatCharacters.setText(characters);

            String screenText = String.format(getContext().getString(R.string.screen_text),
                    detail.getScreenTextTime());
            tvScreenText.setText(screenText);

            String screenTextEffect = String.format(getContext().getString(R.string.screen_text_effect), detail.getScreenTextEffect());
            tvScreenTextEffect.setText(screenTextEffect);

            String kickLevel = String.format(getContext().getString(R.string.kick), detail.getKickLevel().toLowerCase());
            tvKick.setText(kickLevel);

            String frameRateIncrease = String.format(getContext().getString(R.string.fame), String.valueOf(detail.getExpBonus()));
            tvFame.setText(frameRateIncrease);

            // validate next and previous button
            if (index == 0) {
                imgPre.setVisibility(View.INVISIBLE);
            } else {
                imgPre.setVisibility(View.VISIBLE);
            }

            if (index == packages.size() - 1) {
                imgNext.setVisibility(View.INVISIBLE);
            } else {
                imgNext.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBuyPackage(VipPackage vipPackage) {
        if (listener != null) {
            dismiss();
            listener.onBuyPackage(vipPackage);
        }
    }
}
