package vn.puresolutions.livestar.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestar.common.CoinPackageExtension;
import vn.puresolutions.livestar.core.api.model.CoinPackage;
import vn.puresolutions.livestarv3.utilities.old.AmountUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * @author hoangphu
 * @since 11/27/16
 */

public class CoinAdapter extends RecyclerAdapter<CoinPackageExtension, CoinAdapter.ViewHolder> {

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_coin;
    }

    @Override
    protected void updateView(CoinPackageExtension model, ViewHolder viewHolder, int position) {
        CoinPackage coinPackage = model.getCoinPackage();
        viewHolder.tvName.setText(coinPackage.getName());
        AmountUtils.toCoin(viewHolder.tvCoin, coinPackage.getQuantity());
        viewHolder.tvPrice.setText(AmountUtils.toMoney(coinPackage.getPrice()));
        LImageUtils.loadImage(viewHolder.imgPackage, coinPackage.getImage());
        if (model.getPurchase() != null) {
            viewHolder.tvError.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvError.setVisibility(View.GONE);
        }
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.coinPackageItem_tvName)
        TextView tvName;
        @BindView(R.id.coinPackageItem_tvPrice)
        TextView tvPrice;
        @BindView(R.id.coinPackageItem_tvCoin)
        TextView tvCoin;
        @BindView(R.id.coinPackageItem_tvError)
        TextView tvError;
        @BindView(R.id.coinPackageItem_imgPackage)
        SimpleDraweeView imgPackage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
