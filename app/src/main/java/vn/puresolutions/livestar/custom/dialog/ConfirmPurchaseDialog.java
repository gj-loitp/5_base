package vn.puresolutions.livestar.custom.dialog;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.CoinPackage;
import vn.puresolutions.livestar.core.api.model.VipPackage;
import vn.puresolutions.livestar.core.api.model.VipPackageDetails;
import vn.puresolutions.livestarv3.utilities.old.AmountUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * Created by Phu Tran on 4/11/2016.
 */
public class ConfirmPurchaseDialog extends IRoomDialog {

    public interface ConfirmPurchaseDialogListener {
        void onOkButtonClicked();
    }

    @BindView(R.id.confirmBuyVipDialog_tvPackageName)
    TextView tvName;
    @BindView(R.id.confirmBuyVipDialog_imgPackage)
    SimpleDraweeView imgPackage;
    @BindView(R.id.confirmBuyVipDialog_tvPrice)
    TextView tvPrice;
    @BindView(R.id.confirmBuyVipDialog_tvCoin)
    TextView tvCoin;
    @BindView(R.id.confirmBuyVipDialog_tvMessage)
    TextView tvMessage;

    private ConfirmPurchaseDialogListener listener;

    public ConfirmPurchaseDialog(Context context) {
        super(context);
    }

    public void setListener(ConfirmPurchaseDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_confirm_purchase;
    }

    public void setDataSource(VipPackage vipPackage, VipPackageDetails detail) {
        if (UserInfo.getUserLoggedIn().isMBFUser()) {
            String days = getContext().getString(R.string.day).toLowerCase();
            String price = AmountUtils.toMoney(vipPackage.getPrice());
            int numOfDay = Integer.parseInt(vipPackage.getDay());
            tvName.setText(vipPackage.getCode());
            if (numOfDay > 1) {
                price += "/" + numOfDay + " " + days;
            }
            tvPrice.setText(price);
        } else {
            double price = vipPackage.getPrice();
            tvName.setText(detail.getName());
            AmountUtils.toCoin(tvPrice, price);
        }
        tvCoin.setVisibility(View.GONE);
        LImageUtils.loadImage(imgPackage, detail.getImage());
    }

    public void setDataSource(CoinPackage coinPackage) {
        tvName.setText(coinPackage.getName());
        tvPrice.setText(AmountUtils.toMoney(coinPackage.getPrice()));
        AmountUtils.toCoin(tvCoin, coinPackage.getQuantity());
    }

    public void setMessage(String message) {
        tvMessage.setText(Html.fromHtml(message));
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    void onOkButtonClick() {
        super.onOkButtonClick();
        if (listener != null) {
            listener.onOkButtonClicked();
        }
    }

    @Override
    protected int getContainerLayout() {
        return R.layout.dialog_purchase;
    }
}
