package vn.puresolutions.livestar.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestar.core.api.model.Action;
import vn.puresolutions.livestarv3.utilities.old.AmountUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * Created by Phu Tran on 8/23/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public class ActionAdapter extends RecyclerAdapter<Action, ActionAdapter.ViewHolder> {

    public interface OnVotedListener {
        void onVoted(Action action, int position, View view);
    }

    public OnVotedListener listener;

    public void setVotedListener(OnVotedListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_action;
    }

    @Override
    protected void updateView(Action model, ViewHolder viewHolder, int position) {
        LImageUtils.loadImage(viewHolder.imgAction, model.getImage());
        viewHolder.tvName.setText(model.getName());
        int percent = model.getPercent() / 2;
        viewHolder.tvMaxVoted.setText(String.valueOf(model.getMaxVoted()));
        viewHolder.prgPercent.setProgress(percent);
        viewHolder.tvCurrentPercent.setText(String.valueOf(model.getPercent()));
        viewHolder.imgAdd.setOnClickListener(view -> {
            if (listener != null && model.getPercent() < 100) {
                listener.onVoted(model, position, viewHolder.itemView);
            }
        });
        AmountUtils.toCoin(viewHolder.tvPrice, model.getPrice(), model.getDiscount());
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.actionItem_imgAction)
        SimpleDraweeView imgAction;
        @BindView(R.id.actionItem_tvName)
        TextView tvName;
        @BindView(R.id.actionItem_tvCurrentPercent)
        TextView tvCurrentPercent;
        @BindView(R.id.actionItem_prgPercent)
        RoundCornerProgressBar prgPercent;
        @BindView(R.id.actionItem_imgAdd)
        ImageView imgAdd;
        @BindView(R.id.actionItem_tvPrice)
        TextView tvPrice;
        @BindView(R.id.actionItem_tvMaxVoted)
        TextView tvMaxVoted;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
