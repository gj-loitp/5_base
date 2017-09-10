package vn.puresolutions.livestarv3.activity.room.adapter;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.listgift.Item;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

public class GiftsAdapter extends RecyclerView.Adapter<GiftsAdapter.BaseHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<Item> itemList;
    private Context context;
    private final int NUMBER_DISPLAY = 5;
    private int width;

    public interface Callback {
        public void onClick(Item item);
    }

    private Callback callback;

    public GiftsAdapter(Context context, List<Item> itemList, Callback callback) {
        this.itemList = itemList;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_gift, null);
        //width = viewGroup.getMeasuredWidth() / NUMBER_DISPLAY;
        //LLog.d(TAG, "width " + width);
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
            LImageUtils.loadImage(giftHolder.ivGift, item.getImage());
            //giftHolder.ivGift.getLayoutParams().width = width;
            giftHolder.tvCoin.setText(String.valueOf(item.getPrice()));
            giftHolder.tvExpBonus.setText("+" + item.getExp() + "Exp");
            giftHolder.viewGroupMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LAnimationUtil.play(v, Techniques.RubberBand);
                    if (callback != null) {
                        callback.onClick(item);
                    }
                }
            });
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
        private SimpleDraweeView ivGift;
        private LinearLayout viewGroupMain;
        private TextView tvCoin;
        private TextView tvExpBonus;

        public GiftHolder(View view) {
            super(view);
            this.ivGift = (SimpleDraweeView) view.findViewById(R.id.iv_gift);
            this.viewGroupMain = (LinearLayout) view.findViewById(R.id.view_group_main);
            this.tvCoin = (TextView) view.findViewById(R.id.tv_coin);
            this.tvExpBonus = (TextView) view.findViewById(R.id.tv_exp_bonus);
        }
    }
}