package vn.puresolutions.livestarv3.activity.homescreen.model.idolsearch;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;
import vn.puresolutions.livestarv3.view.LTextViewImageRight;

/**
 * File created on 8/3/2017.
 *
 * @author Anhdv
 */

public class IdolSearchResultAdapter extends RecyclerView.Adapter<IdolSearchResultAdapter.IdolSearchResultHolder> {
    private Context mContext;
    private ArrayList<Room> lstRoom;
    private Callback callback;

    public IdolSearchResultAdapter(Context mContext, ArrayList<Room> lstRoom, Callback callback) {
        this.mContext = mContext;
        this.lstRoom = lstRoom;
        this.callback = callback;
    }

    @Override
    public IdolSearchResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_search_result, null);
        return new IdolSearchResultHolder(v);
    }

    @Override
    public void onBindViewHolder(IdolSearchResultHolder holder, int position) {
        Room room = lstRoom.get(position);
        holder.tvIdolName.setTypeface(null, Typeface.BOLD);
        holder.tvDescription.setTypeface(null, Typeface.ITALIC);
        holder.tvIdolName.setText(room.getUser().getName());
        LImageUtils.loadImage(holder.sdvAvatar, (String) room.getUser().getAvatarsPath().getW300h300());
        holder.ltvAction.setTextAllCap();
        holder.ltvAction.setImageSize(10);
        if (room.isOnAir()) {
            holder.ivOnAir.setVisibility(View.VISIBLE);
            holder.tvDescription.setText(mContext.getString(R.string.search_description_onAir));
            holder.ltvAction.setText(mContext.getString(R.string.search_action_room));
            holder.ltvAction.setBackground(R.drawable.background_loginscreen_btnlogin);
            switch (room.getMode()) {
                case Constants.MODE_PUBLIC:
                    holder.ivPrivate.setVisibility(View.INVISIBLE);
                    break;
                case Constants.MODE_RESTRICT:
                    holder.ivPrivate.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            holder.ivOnAir.setVisibility(View.INVISIBLE);
            holder.tvDescription.setText(mContext.getString(R.string.search_description_offAir));
            holder.ltvAction.setText(mContext.getString(R.string.search_action_resume));
            holder.ltvAction.setBackground(R.drawable.bt_violet);
            holder.ivPrivate.setVisibility(View.INVISIBLE);
        }

        holder.ltvAction.setOnItemClick(new LTextViewHorizontal.Callback() {
            @Override
            public void OnClickItem() {
                LAnimationUtil.play(holder.rlMain, Techniques.Pulse, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        if (callback != null) {
                            callback.onClick(room);
                        }
                    }

                    @Override
                    public void onRepeat() {
                        //do nothing
                    }

                    @Override
                    public void onStart() {
                        //do nothing
                    }
                });
            }
        });
        if (position == lstRoom.size() - 1) {
            holder.viewSpaceBottom.setVisibility(View.VISIBLE);
        } else {
            holder.viewSpaceBottom.setVisibility(View.GONE);
        }
        if (position == 0) {
            holder.viewSpaceTop.setVisibility(View.VISIBLE);
        } else {
            holder.viewSpaceTop.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        int size = null != lstRoom ? lstRoom.size() : 0;
        return size;
    }

    public interface Callback {
        public void onClick(Room room);
    }

    public class IdolSearchResultHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvSearchScreen_Description)
        TextView tvDescription;
        @BindView(R.id.tvSearchScreen_IdolName)
        TextView tvIdolName;
        @BindView(R.id.ltvSearchScreen_Action)
        LTextViewImageRight ltvAction;
        @BindView(R.id.ivSearchScreen_OnAir)
        ImageView ivOnAir;
        @BindView(R.id.ivSearchScreen_Private)
        ImageView ivPrivate;
        @BindView(R.id.sdvSearchScreen_Avatar)
        SimpleDraweeView sdvAvatar;
        @BindView(R.id.view_space_bottom)
        View viewSpaceBottom;
        @BindView(R.id.view_space_top)
        View viewSpaceTop;
        @BindView(R.id.rl_main)
        RelativeLayout rlMain;

        public IdolSearchResultHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //LAnimationUtil.slideInUp(mContext, itemView);
        }
    }
}
