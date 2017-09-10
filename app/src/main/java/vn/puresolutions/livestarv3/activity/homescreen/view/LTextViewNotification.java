package vn.puresolutions.livestarv3.activity.homescreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import vn.puresolutions.livestar.R;

/**
 * File created on 8/2/2017.
 *
 * @author Anhdv
 */

public class LTextViewNotification extends RelativeLayout implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private Callback callback;
    @BindView(R.id.sdvNotiScreen_Icon)
    SimpleDraweeView sdvIcon;
    @BindView(R.id.tvNotiScreen_Description)
    TextView tvDescription;
    @BindView(R.id.tvNotiScreen_Date)
    TextView tvDate;
    @BindView(R.id.lnlNotiItem)
    LinearLayout lnlNtiItem;

    public LTextViewNotification(Context context) {
        super(context);
        init();
    }

    public LTextViewNotification(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LTextViewNotification(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_text_view_notification, this);
        lnlNtiItem.setOnClickListener(this);
    }

    public SimpleDraweeView getIcon() {
        return sdvIcon;
    }

    public TextView getTvDescription() {
        return tvDescription;
    }

    public TextView getTvDate() {
        return tvDate;
    }

    public void setTvDescription(String description) {
        this.tvDescription.setText(description);
    }

    public void setTvDate(String date) {
        this.tvDate.setText(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnlNotiItem:
                if (callback != null) {
                    callback.OnClickItem();
                }
                break;
        }
    }

    private void onclickItem() {
        if (callback != null) {
            callback.OnClickItem();
        }
    }

    private interface Callback {
        public void OnClickItem();
    }
}
