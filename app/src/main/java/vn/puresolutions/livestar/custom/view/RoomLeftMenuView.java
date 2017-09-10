package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.fragment.RoomFragment;

/**
 * @author hoangphu
 * @since 9/22/16
 */
public class RoomLeftMenuView extends IRoomView {

    public interface LeftMenuListener {
        void onShowAudiences();
        void onShowCalendar();
        void onPurchaseCoin();
    }

    @BindView(R.id.roomLeftMenuView_vwAudiences)
    View vwAudience;

    private LeftMenuListener listener;

    public RoomLeftMenuView(Context context) {
        super(context);
        init(context);
    }

    public RoomLeftMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RoomLeftMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setListener(LeftMenuListener listener) {
        this.listener = listener;
    }

    @Override
    public void loadData() {
        // DO NOTHING
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_room_left_menu, this);
        ButterKnife.bind(this);

        setOnTouchListener((view, motionEvent) -> true);
    }

    @OnClick(R.id.roomLeftMenuView_vwAudiences)
    void onOpenAudience() {
        if (listener != null) {
            listener.onShowAudiences();
        }
    }

    @OnClick(R.id.roomLeftMenuView_vwCalendar)
    void onShowCalendar() {
        if (listener != null) {
            listener.onShowCalendar();
        }
    }

    @OnClick(R.id.roomLeftMenuView_vwPurchase)
    void onPurchaseCoin() {
        if (listener != null) {
            listener.onPurchaseCoin();
        }
    }
}
