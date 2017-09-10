package vn.puresolutions.livestar.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestar.core.api.model.GiftQuantity;

/**
 * @author hoangphu
 * @since 8/20/16
 */
public class GiftQuantityAdapter extends RecyclerAdapter<GiftQuantity, GiftQuantityAdapter.ViewHolder> {

    private int selectedPos = 0;

    public GiftQuantityAdapter() {
        super();
    }

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_gift_quantity;
    }

    public void setSelectedPos(int pos) {
        int currentPos = selectedPos;
        selectedPos = pos;
        notifyItemChanged(pos);
        notifyItemChanged(currentPos);
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    @Override
    protected void updateView(GiftQuantity model, ViewHolder viewHolder, int position) {
        viewHolder.tvQuantity.setText(String.valueOf(model.getQuantity()));
        viewHolder.imgCover.setImageResource(model.getResourceId());
        if (selectedPos == position) {
            viewHolder.tvQuantity.setBackgroundResource(R.drawable.background_gift_quantity_selected);
        } else {
            viewHolder.tvQuantity.setBackgroundResource(0);
        }
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.giftQuantityItem_tvQuantity)
        public TextView tvQuantity;
        @BindView(R.id.giftQuantityItem_imgCover)
        public ImageView imgCover;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
