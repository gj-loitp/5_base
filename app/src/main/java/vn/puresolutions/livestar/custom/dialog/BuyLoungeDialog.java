package vn.puresolutions.livestar.custom.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import butterknife.BindView;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;

/**
 * Created by Phu Tran on 8/17/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public class BuyLoungeDialog extends IRoomDialog {

    public interface BuyLoungeDialogListener {
        void onOkButtonClicked(int price);
    }

    @BindView(R.id.dialogBuyLounge_edtPrice)
    EditText edtPrice;

    private BuyLoungeDialogListener dialogListener;

    public BuyLoungeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.lounge_price);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_buy_lounge;
    }

    public void setPrice(long price) {
        edtPrice.setText(String.valueOf(price));
        edtPrice.setSelection(edtPrice.getText().length());
    }

    public void setDialogListener(BuyLoungeDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    void onOkButtonClick() {
        super.onOkButtonClick();
        if (TextUtils.isEmpty(edtPrice.getText().toString())
                || Integer.parseInt(edtPrice.getText().toString()) <= 0) {
            AlertMessage.showError(getContext(), R.string.please_input_price);
            return;
        }
        if (dialogListener != null) {
            dialogListener.onOkButtonClicked(Integer.parseInt(edtPrice.getText().toString()));
        }
    }
}
