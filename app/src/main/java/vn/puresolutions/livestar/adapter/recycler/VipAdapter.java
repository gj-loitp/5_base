package vn.puresolutions.livestar.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.VipPackage;
import vn.puresolutions.livestar.core.api.model.VipPackageDetails;
import vn.puresolutions.livestarv3.utilities.old.AmountUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * @author hoangphu
 * @since 11/27/16
 */

public class VipAdapter extends RecyclerAdapter<VipPackage, VipAdapter.ViewHolder> {

    private HashMap<Long, VipPackageDetails> details;

    public HashMap<Long, VipPackageDetails> getDetails() {
        return details;
    }

    public void setDetails(HashMap<Long, VipPackageDetails> details) {
        this.details = details;
    }

    public VipAdapter() {
        super();
    }

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_vip;
    }

    @Override
    protected void updateView(VipPackage vipPackage, ViewHolder viewHolder, int position) {
        String days = viewHolder.itemView.getContext().getString(R.string.day).toLowerCase();
        VipPackageDetails detail = details.get(vipPackage.getId());
        if (UserInfo.getUserLoggedIn().isMBFUser()) {
            String name = vipPackage.getCode() + " - " + vipPackage.getDay() + " " + days;
            String price = AmountUtils.toMoney(vipPackage.getPrice());
            int numOfDay = Integer.parseInt(vipPackage.getDay());
            viewHolder.tvName.setText(name);
            if (numOfDay > 1) {
                price += "/" + numOfDay + " " + days;
            }
            viewHolder.tvPrice.setText(price);
        } else {
            double price = vipPackage.getPrice();
            String name = detail.getName() + " - " + vipPackage.getDay() + " " + days;
            viewHolder.tvName.setText(name);
            AmountUtils.toCoin(viewHolder.tvPrice, price);
        }
        LImageUtils.loadImage(viewHolder.imgPackage, detail.getImage());
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vipPackageItem_tvName)
        TextView tvName;
        @BindView(R.id.vipPackageItem_tvPrice)
        TextView tvPrice;
        @BindView(R.id.vipPackageItem_imgPackage)
        SimpleDraweeView imgPackage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
