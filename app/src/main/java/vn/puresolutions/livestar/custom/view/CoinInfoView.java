package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestarv3.utilities.old.AmountUtils;

/**
 * Created by Phu Tran on 4/11/2016.
 */
public class CoinInfoView extends RelativeLayout {

    public enum Type {
        NONE,
        COIN,
        MONEY
    }

    @BindView(R.id.coinInfoView_tvCoin)
    TextView tvCoin;
    @BindView(R.id.coinInfoView_tvUsername)
    TextView tvUsername;

    private Type type = Type.NONE;

    public CoinInfoView(Context context) {
        super(context);
        init(context);
    }

    public CoinInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CoinInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_coin_info, this, true);
        ButterKnife.bind(this);
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setCoinValue(long value) {
        switch (type) {
            case COIN:
                AmountUtils.toCoin(tvCoin, value);
                break;
            case MONEY:
                tvCoin.setText(AmountUtils.toMoney(value));
                break;
        }

        String username = UserInfo.getUserLoggedIn().getName();
        if (!TextUtils.isEmpty(username)) {
            tvUsername.setVisibility(View.VISIBLE);
            tvUsername.setText(username);
        }
    }
}
