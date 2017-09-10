package vn.puresolutions.livestarv3.activity.bank.manager;

/**
 * Created by loitp
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

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.BaseHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<Item> itemList;
    private Activity activity;

    public interface GiftCallback {
        public void onClick(Item item);

        public void onIsLastItem(int maxSize);
    }

    private GiftCallback giftCallback;

    public GiftAdapter(Activity activity, List<Item> itemList, GiftCallback giftCallback) {
        this.itemList = itemList;
        this.activity = activity;
        this.giftCallback = giftCallback;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = null;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_bank_gift, null);
        //int height = viewGroup.getMeasuredHeight() / 3;
        //v.setMinimumHeight(height);
        return new GiftHolder(v);
    }

    @Override
    public void onBindViewHolder(BaseHolder baseHolder, int position) {
        if (baseHolder == null) {
            LLog.d(TAG, "onBindViewHolder baseHolder == null");
            return;
        }
        Item item = itemList.get(position);
        if (baseHolder instanceof GiftHolder) {
            GiftHolder giftHolder = (GiftHolder) baseHolder;
            LImageUtils.loadImage(giftHolder.ivAvatar, item.getGifts().getImage());
            giftHolder.tvTime.setText(item.getUpdatedAt());
            LUIUtil.setMarquee(giftHolder.tvTime);
            giftHolder.tvCostValue.setText(String.valueOf(item.getGifts().getPrice()));
            giftHolder.tvNumberValue.setText(String.valueOf(item.getQuantity()));
            giftHolder.tvTotalCostValue.setText(String.valueOf(item.getTotalCost()));
            giftHolder.viewGroupMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (giftCallback != null) {
                        giftCallback.onClick(item);
                    }
                }
            });
            if (position == itemList.size() - 1) {
                if (giftCallback != null) {
                    giftCallback.onIsLastItem(itemList.size());
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

    public class GiftHolder extends BaseHolder {
        private SimpleDraweeView ivAvatar;
        private TextView tvTime;
        private TextView tvCostValue;
        private TextView tvNumberValue;
        private TextView tvTotalCostValue;
        private LinearLayout viewGroupMain;

        public GiftHolder(View view) {
            super(view);
            this.viewGroupMain = (LinearLayout) view.findViewById(R.id.view_group_main);
            this.ivAvatar = (SimpleDraweeView) view.findViewById(R.id.iv_avatar);
            this.tvTime = (TextView) view.findViewById(R.id.tv_time);
            this.tvCostValue = (TextView) view.findViewById(R.id.tv_cost_value);
            this.tvNumberValue = (TextView) view.findViewById(R.id.tv_number_value);
            this.tvTotalCostValue = (TextView) view.findViewById(R.id.tv_total_cost_value);
            //LAnimationUtil.slideInUp(activity, view);
        }
    }
}