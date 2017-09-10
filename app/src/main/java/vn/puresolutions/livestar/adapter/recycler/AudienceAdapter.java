package vn.puresolutions.livestar.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestar.core.api.model.UserSocket;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.old.VipUtils;

/**
 * @author hoangphu
 * @since 9/24/16
 */
public class AudienceAdapter extends RecyclerAdapter<UserSocket, AudienceAdapter.ViewHolder> {

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_audience;
    }

    @Override
    protected void updateView(UserSocket model, ViewHolder viewHolder, int position) {
        viewHolder.tvUsername.setText(model.getName());
        // TODO: change to real user money value
        //AmountUtils.toCoin(viewHolder.tvPrice, 2000, true);
        LImageUtils.loadAvatar(viewHolder.imgAvatar, model.getId());
        VipUtils.styleTextVip(viewHolder.tvUsername, model.getVip(), android.R.color.white);
        VipUtils.setIconVip(viewHolder.imgVip, model.getVip());
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.audienceItem_imgVip)
        ImageView imgVip;
        @BindView(R.id.audienceItem_imgAvatar)
        SimpleDraweeView imgAvatar;
        @BindView(R.id.audienceItem_tvPrice)
        TextView tvPrice;
        @BindView(R.id.audienceItem_tvUsername)
        TextView tvUsername;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
