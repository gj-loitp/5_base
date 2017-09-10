package vn.puresolutions.livestar.adapter.recycler;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.model.Gift;
import vn.puresolutions.livestarv3.utilities.old.AmountUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * Created by Phu Tran on 8/17/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public class GiftAdapter extends LoadingItemAdapter<Gift, GiftAdapter.ViewHolder> {

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_gift;
    }

    @Override
    protected void updateView(Gift model, ViewHolder viewHolder, int position) {
        super.updateView(model, viewHolder, position);
        LImageUtils.loadImage(viewHolder.imgGift, model.getImage());
        AmountUtils.toCoin(viewHolder.tvPrice, model.getPrice(), model.getDiscount(), true);
    }

    @Override
    protected void onLoadingModeChange(ViewHolder viewHolder, boolean isEnable) {
        viewHolder.imgGift.setVisibility(isEnable ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container);
    }

    static class ViewHolder extends LoadingItemAdapter.ViewHolder {
        @BindView(R.id.giftItem_imgGift)
        SimpleDraweeView imgGift;
        @BindView(R.id.giftItem_tvPrice)
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
