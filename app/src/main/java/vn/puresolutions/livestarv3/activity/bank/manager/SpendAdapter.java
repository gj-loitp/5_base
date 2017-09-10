package vn.puresolutions.livestarv3.activity.bank.manager;

/**
 * Created loitp
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.gifthistory.Item;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

public class SpendAdapter extends RecyclerView.Adapter<SpendAdapter.BaseHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<Item> itemList;
    private Activity activity;

    public interface SpendCallback {
        public void onClick(Item item);

        public void onIsLastItem(int maxSize);
    }

    private SpendCallback spendCallback;

    public SpendAdapter(Activity activity, List<Item> itemList, SpendCallback spendCallback) {
        this.itemList = itemList;
        this.activity = activity;
        this.spendCallback = spendCallback;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = null;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_bank_spend, null);
        //int height = viewGroup.getMeasuredHeight() / 3;
        //v.setMinimumHeight(height);
        return new PayHolder(v);
    }

    @Override
    public void onBindViewHolder(BaseHolder baseHolder, int position) {
        if (baseHolder == null) {
            LLog.d(TAG, "onBindViewHolder baseHolder == null");
            return;
        }
        Item item = itemList.get(position);
        if (baseHolder instanceof PayHolder) {
            PayHolder payHolder = (PayHolder) baseHolder;
            LImageUtils.loadImage(payHolder.ivAvatar, item.getGifts().getImage());
            payHolder.tvTime.setText(item.getUpdatedAt());
            LUIUtil.setMarquee(payHolder.tvTime);
            payHolder.tvDescription.setText("Tặng " + item.getGifts().getName() + " cho " + item.getToUser());
            LUIUtil.setMarquee(payHolder.tvDescription);
            payHolder.tvNumberValue.setText(String.valueOf(item.getQuantity()));
            payHolder.tvCostPerUnitValue.setText(String.valueOf(item.getGifts().getPrice()));
            payHolder.tvTotalCostValue.setText(String.valueOf(item.getTotalCost()));
            payHolder.viewGroupMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (spendCallback != null) {
                        spendCallback.onClick(item);
                    }
                }
            });
            if (position == itemList.size() - 1) {
                if (spendCallback != null) {
                    spendCallback.onIsLastItem(itemList.size());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        public BaseHolder(View itemView) {
            super(itemView);
        }
    }

    public class PayHolder extends BaseHolder {
        private SimpleDraweeView ivAvatar;
        private TextView tvTime;
        private TextView tvDescription;
        private TextView tvNumberValue;
        private TextView tvCostPerUnitValue;
        private TextView tvTotalCostValue;
        private LinearLayout viewGroupMain;

        public PayHolder(View view) {
            super(view);
            this.viewGroupMain = (LinearLayout) view.findViewById(R.id.view_group_main);
            this.ivAvatar = (SimpleDraweeView) view.findViewById(R.id.iv_avatar);
            this.tvTime = (TextView) view.findViewById(R.id.tv_time);
            this.tvDescription = (TextView) view.findViewById(R.id.tv_description);
            this.tvNumberValue = (TextView) view.findViewById(R.id.tv_number_value);
            this.tvCostPerUnitValue = (TextView) view.findViewById(R.id.tv_cost_per_unit_value);
            this.tvTotalCostValue = (TextView) view.findViewById(R.id.tv_total_cost_value);
            //LAnimationUtil.slideInUp(activity, view);
        }
    }
}