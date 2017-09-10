package vn.puresolutions.livestar.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.VipPackage;
import vn.puresolutions.livestarv3.utilities.old.AmountUtils;

/**
 * @author hoangphu
 * @since 12/3/16
 */

public class RelatedPackageAdapter extends RecyclerAdapter<VipPackage, RelatedPackageAdapter.ViewHolder> {

    public interface OnBuyPackageListener {
        void onBuyPackage(VipPackage vipPackage);
    }

    private OnBuyPackageListener listener;

    public RelatedPackageAdapter() {
        super();
    }

    public void setListener(OnBuyPackageListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_vip_related_package;
    }

    @Override
    protected void updateView(VipPackage model, ViewHolder viewHolder, int position) {
        Context context = viewHolder.tvTitle.getContext();
        String title = String.format(context.getString(R.string.related_package_format), model.getDay());
        viewHolder.tvTitle.setText(title);

        if (UserInfo.getUserLoggedIn().isMBFUser()) {
            String price = AmountUtils.toMoney(model.getPrice());
            String bonusPrice = AmountUtils.toMoney(model.getDiscount());
            viewHolder.tvPrice.setText(price);
            viewHolder.tvBonusPrice.setText(bonusPrice);
        } else {
            double price = model.getPrice();
            double bonusPrice = model.getDiscount();
            AmountUtils.toCoin(viewHolder.tvPrice, price);
            AmountUtils.toCoin(viewHolder.tvBonusPrice, bonusPrice);
        }

        viewHolder.btnBuy.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBuyPackage(model);
            }
        });
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.relatedPackageItem_tvTitle)
        TextView tvTitle;
        @BindView(R.id.relatedPackageItem_tvPrice)
        TextView tvPrice;
        @BindView(R.id.relatedPackageItem_tvBonusPrice)
        TextView tvBonusPrice;
        @BindView(R.id.relatedPackageItem_btnBuy)
        Button btnBuy;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
