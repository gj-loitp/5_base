package vn.puresolutions.livestar.adapter.recycler;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.model.Lounge;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestarv3.utilities.old.AmountUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * @author hoangphu
 * @since 8/29/16
 */
public class LoungeAdapter extends LoadingItemAdapter<Lounge, LoungeAdapter.ViewHolder> {

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_lounge;
    }

    @Override
    protected void updateView(Lounge model, ViewHolder viewHolder, int position) {
        super.updateView(model, viewHolder, position);
        Context context = viewHolder.imgAvatar.getContext();
        User user = model.getUser();
        if (user.getId() > 0) {
            // int color = VipUtils.getVipColor(user.getVip().getVip(), R.color.white);
            // viewHolder.tvName.setTextColor(ContextCompat.getColor(context, color));
            viewHolder.tvName.setText(user.getName());
            viewHolder.imgAvatar.setVisibility(View.VISIBLE);
            LImageUtils.loadAvatar(viewHolder.imgAvatar, user.getId());
        } else {
            // viewHolder.tvName.setTextColor(ContextCompat.getColor(context, R.color.white));
            viewHolder.tvName.setText(context.getString(R.string.guest));
            viewHolder.imgAvatar.setVisibility(View.INVISIBLE);
        }
        AmountUtils.toCoin(viewHolder.tvPrice, model.getCost(), true);
    }

    @Override
    protected void onLoadingModeChange(ViewHolder viewHolder, boolean isEnable) {
        int visible = isEnable ? View.INVISIBLE : View.VISIBLE;
        viewHolder.imgAvatar.setVisibility(visible);
        viewHolder.tvName.setVisibility(visible);
        viewHolder.tvPrice.setVisibility(visible);
        viewHolder.imgChair.setVisibility(visible);
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container);
    }

    static class ViewHolder extends LoadingItemAdapter.ViewHolder {

        @BindView(R.id.loungesItem_imgAvatar)
        SimpleDraweeView imgAvatar;
        @BindView(R.id.loungesItem_tvName)
        TextView tvName;
        @BindView(R.id.loungesItem_tvPrice)
        TextView tvPrice;
        @BindView(R.id.loungesItem_imgChair)
        ImageView imgChair;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
