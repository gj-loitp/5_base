package vn.puresolutions.livestarv3.activity.userprofile.adapter;

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
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * File created on 8/9/2017.
 *
 * @author anhdv
 */

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowHolder> {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<Room> lstUserFollow;
    private Callback callback;
    private Context mContext;

    public FollowAdapter(Context mContext, ArrayList<Room> lstUserFollow, Callback callback) {
        this.mContext = mContext;
        this.lstUserFollow = lstUserFollow;
        this.callback = callback;
    }

    @Override
    public FollowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_follower_following, null);
        FollowHolder followHolder = new FollowHolder(v);
        return followHolder;
    }

    @Override
    public void onBindViewHolder(FollowHolder holder, int position) {
        Room room = lstUserFollow.get(position);
        //LLog.d(TAG, "img: " + room.getUser().getAvatar());
        LImageUtils.loadImage(holder.sdvAvatar, room.getUser().getAvatarsPath().getW300h300());
        holder.tvName.setText(room.getUser().getName());
        holder.tvName.setTypeface(null, Typeface.BOLD);
        if (room.isOnAir()) {
            holder.tvStatus.setVisibility(View.VISIBLE);
        } else {
            holder.tvStatus.setVisibility(View.GONE);
        }
        holder.tvCount.setText(String.valueOf(room.getFollow()));
        holder.imvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        if (!room.isOnAir()) {
            holder.imvisOnAir.setImageResource(R.drawable.livestream_frame_blue);
            holder.imvAction.setImageResource(R.drawable.next_button_blue);
        } else {
            holder.imvisOnAir.setImageResource(R.drawable.livestream_frame);
            holder.imvAction.setImageResource(R.drawable.next_button_livestream);
        }
        if (position == 0) {
            holder.viewSpace.setVisibility(View.VISIBLE);
        } else {
            holder.viewSpace.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != lstUserFollow ? lstUserFollow.size() : 0);
    }

    public interface Callback {
        public void onClick(Room room);
    }

    public class FollowHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdvFollow_Avatar)
        SimpleDraweeView sdvAvatar;
        @BindView(R.id.tvFollow_Name)
        TextView tvName;
        @BindView(R.id.tvFollow_Status)
        TextView tvStatus;
        @BindView(R.id.tvFollow_Count)
        TextView tvCount;
        @BindView(R.id.imvFollow_Action)
        ImageView imvAction;
        @BindView(R.id.imvFollow_isOnAir)
        ImageView imvisOnAir;
        @BindView(R.id.view_space)
        View viewSpace;
        @BindView(R.id.rl_main)
        RelativeLayout rlMain;

        public FollowHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //LAnimationUtil.slideInUp(mContext, itemView);
        }
    }
}
