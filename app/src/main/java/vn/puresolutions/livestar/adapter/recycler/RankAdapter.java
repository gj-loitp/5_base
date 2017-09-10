package vn.puresolutions.livestar.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.model.Broadcaster;
import vn.puresolutions.livestar.core.api.model.LoyalFan;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * File created on 6/29/2017.
 *
 * @author Anhdv
 */

public class RankAdapter<T> extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {
    List<T> mlstObject = new ArrayList<>();
    private Context mcontext;
    public RankAdapter(Context context, List<T> lstObject) {
        mlstObject=lstObject;
        mcontext=context;
    }
    @Override
    public RankAdapter.RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.list_item_rank, parent, false);
        return new RankViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RankAdapter.RankViewHolder holder, int position) {
        if (mlstObject.get(position) instanceof Broadcaster){
            Broadcaster broacaster = (Broadcaster) mlstObject.get(position);
            holder.tvName.setText(broacaster.getName());
            holder.tvHeart.setText(String.valueOf(broacaster.getHeart()));
            LImageUtils.loadImage(holder.sdvAvatar, broacaster.getAvatar());
        }else if(mlstObject.get(position) instanceof LoyalFan){
            LoyalFan loyalFan = (LoyalFan) mlstObject.get(position);
            holder.tvName.setText(loyalFan.getName());
            if (loyalFan.getTotalMoney()>0){
                holder.tvHeart.setText(String.valueOf(loyalFan.getTotalMoney()));
                holder.imvRankType.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_coin));
            }else{
                holder.tvHeart.setText(String.valueOf(loyalFan.getCount()));
                holder.imvRankType.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ic_share_selected));
            }
            LImageUtils.loadImage(holder.sdvAvatar, loyalFan.getAvatar());

        }
        holder.tvRank.setText(String.valueOf(position+1));
        switch (position){
            case 0:
                holder.imvRankOne.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.imvRankTwo.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.imvRankThree.setVisibility(View.VISIBLE);
                break;
            default:
                holder.rlRankNormal.setVisibility(View.VISIBLE);

        }
/*        if (position==0){
            holder.imvRankOne.setVisibility(View.VISIBLE);
        }else if(position==1){

        }else if()*/
    }

    @Override
    public int getItemCount() {
        return mlstObject.size();
    }

    public class RankViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.sdvRankAvatar)
        SimpleDraweeView sdvAvatar;
        @BindView(R.id.imvRankOne)
        ImageView imvRankOne;
        @BindView(R.id.imvRankTwo)
        ImageView imvRankTwo;
        @BindView(R.id.imvRankThree)
        ImageView imvRankThree;
        @BindView(R.id.imvRankType)
        ImageView imvRankType;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvHeart)
        TextView tvHeart;
        @BindView(R.id.tvRank)
        TextView tvRank;
        @BindView(R.id.rlRankNormal)
        RelativeLayout rlRankNormal;
        public RankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
