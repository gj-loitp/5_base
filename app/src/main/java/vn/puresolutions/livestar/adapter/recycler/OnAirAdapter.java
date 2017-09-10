package vn.puresolutions.livestar.adapter.recycler;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.activity.RoomActivity;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.core.api.model.Room;
import vn.puresolutions.livestar.fragment.OnAirFragment;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * @author hoangphu
 * @since 8/1/16
 */
public class OnAirAdapter extends RecyclerAdapter<Room, OnAirAdapter.ViewHolder> {

    private int baseWidth;
    private OnAirFragment.OnAirLoadMoreListener listener;
    private boolean reachToEnd;
    private boolean loadingMore;

    public OnAirAdapter() {
        super();
    }

    public void setListener(OnAirFragment.OnAirLoadMoreListener listener) {
        this.listener = listener;
    }

    public void finishLoadingMore() {
        loadingMore = false;
        notifyDataSetChanged();
    }

    public void reachToEnd() {
        reachToEnd = true;
        notifyDataSetChanged();
    }

    @Override
    protected int getLayoutItem() {
        return R.layout.list_item_on_air;
    }

    @Override
    protected void updateView(Room room, ViewHolder viewHolder, final int position) {
        if (isLastPosition(position)) {
            viewHolder.prgLoading.setVisibility(View.VISIBLE);
            viewHolder.imgRoomCover.setVisibility(View.GONE);
            viewHolder.lnlInfo.setVisibility(View.GONE);

            if (!loadingMore) {
                loadingMore = true;
                listener.onLoadingMore();
            }
        } else {
            viewHolder.prgLoading.setVisibility(View.GONE);
            viewHolder.imgRoomCover.setVisibility(View.VISIBLE);
            viewHolder.lnlInfo.setVisibility(View.VISIBLE);

            viewHolder.tvRoomName.setText(room.getTitle());
            viewHolder.tvLikeNumber.setText(String.valueOf(room.getBroadcaster().getHeart()));
            viewHolder.tvViewNumber.setText(String.valueOf(room.getTotalUser()));
            LImageUtils.loadImage(viewHolder.imgRoomCover, room.getPoster());

            if (room.isOnAir()) {
                viewHolder.lnlOnAir.setVisibility(View.VISIBLE);
            } else {
                viewHolder.lnlOnAir.setVisibility(View.GONE);
            }

            // Date on air
            if (!TextUtils.isEmpty(room.getDate()) && !TextUtils.isEmpty(room.getStart())) {
                viewHolder.lnlDateTimeOnAir.setVisibility(View.VISIBLE);
                viewHolder.lnlOnAir.setVisibility(View.GONE);
                viewHolder.tvDateOnAir.setText(room.getDate());
                viewHolder.tvTimeOnAir.setText(room.getStart());
            } else {
                viewHolder.lnlDateTimeOnAir.setVisibility(View.GONE);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                viewHolder.imgRoomCover.setTransitionName(room.getTitle());
                viewHolder.itemView.setTag(viewHolder.imgRoomCover);
            }
            viewHolder.itemView.setOnClickListener(view -> startRoomScreen(view.getContext(), (View) view.getTag(), position));
        }
    }

    public void setBaseWidth(int baseWidth) {
        this.baseWidth = baseWidth;
    }

    @Override
    public int getActualItemCount() {
        return getModelSize() + (!reachToEnd && getModelSize() > 0 ? 1 : 0);
    }

    public int getModelSize() {
        return items.size();
    }

    @Override
    public Room getItem(int position) {
        if (isLastPosition(position)) {
            return null;
        } else {
            return super.getItem(position);
        }
    }

    public boolean isLastPosition(int position) {
        return position >= getModelSize();
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container, baseWidth);
    }

    private void startRoomScreen(Context context, View transitionView, int postion) {
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra(RoomActivity.BUNDLE_KEY_ROOM, getItem(postion));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) context, transitionView, transitionView.getTransitionName());
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.onAirItem_tvRoomName)
        TextView tvRoomName;
        @BindView(R.id.onAirItem_tvLikeNumber)
        TextView tvLikeNumber;
        @BindView(R.id.onAirItem_tvViewNumber)
        TextView tvViewNumber;
        @BindView(R.id.onAirItem_imgRoomCover)
        SimpleDraweeView imgRoomCover;
        @BindView(R.id.onAirItem_lnlOnAir)
        View lnlOnAir;
        @BindView(R.id.onAirItem_lnlDateTimeOnAir)
        View lnlDateTimeOnAir;
        @BindView(R.id.onAirItem_tvDateOnAir)
        TextView tvDateOnAir;
        @BindView(R.id.onAirItem_tvTimeOnAir)
        TextView tvTimeOnAir;
        @BindView(R.id.onAirItem_prgLoading)
        ProgressBar prgLoading;
        @BindView(R.id.onAirItem_lnlInfo)
        View lnlInfo;

        public ViewHolder(final View itemView, int baseWidth) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (baseWidth > 0) {
                // adjust image view size base on constants ratio
                int containerWidth = (int) (baseWidth * Constants.ON_AIR_ITEM_SCALE_WIDTH_RATIO);
                int containerHeight = containerWidth * 16 / 9;
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(containerWidth, containerHeight);
                itemView.setLayoutParams(params);
                imgRoomCover.getLayoutParams().width = containerWidth;
            } else {
                throw new IllegalArgumentException("Base size cannot equal zero");
            }
        }
    }
}
