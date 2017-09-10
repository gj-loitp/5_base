package vn.puresolutions.livestarv3.activity.homescreen.model.idolroombycategory;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;

public class IdolRoomByCategoryAdapter extends RecyclerView.Adapter<IdolRoomByCategoryAdapter.IdolRoomByCategoryDetailHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<Room> roomList;
    private Activity activity;
    private Callback callback;
    //private int widthViewHolder;

    public interface Callback {
        public void onClick(Room room, int position, List<Room> roomList);
    }

    public IdolRoomByCategoryAdapter(Activity activity, List<Room> roomList, Callback callback) {
        this.roomList = roomList;
        this.activity = activity;
        this.callback = callback;

        //int screenWidthInDp = (int) ScreenUtils.convertPixelsToDp((float) (ScreenUtils.getWidthScreen(activity) / 4.5), activity);
        //widthViewHolder = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, screenWidthInDp, activity.getResources().getDisplayMetrics());//200dp
    }

    @Override
    public IdolRoomByCategoryDetailHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_room_by_category, null);
        //int width = (int) (viewGroup.getMeasuredWidth() / 4.5);
        //v.setMinimumWidth(width);
        IdolRoomByCategoryDetailHolder idolRoomByCategoryDetailHolder = new IdolRoomByCategoryDetailHolder(v);
        return idolRoomByCategoryDetailHolder;
    }

    @Override
    public void onBindViewHolder(IdolRoomByCategoryDetailHolder holder, int position) {
        Room room = roomList.get(position);
        if (room.getBanners() != null && room.getBanners().getBanner() != null) {
            LImageUtils.loadImage(holder.iv, room.getBanners().getW720h415());
        }
        final String des = room.getTitle();
        holder.tvName.setText(des);
        LUIUtil.setTextShadow(holder.tvName);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    LAnimationUtil.play(v, Techniques.Pulse, new LAnimationUtil.Callback() {
                        @Override
                        public void onCancel() {
                            //do nothing
                        }

                        @Override
                        public void onEnd() {
                            callback.onClick(room, position, roomList);
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
            }
        });
        holder.lTextViewHorizontalWatching.setImageSize(18);
        //TODO get watching
        if (room.getSession() != null) {
            holder.lTextViewHorizontalWatching.setText(String.valueOf(room.getSession().getView()));
        } else {
            holder.lTextViewHorizontalWatching.setText("0");
        }
        holder.lTextViewHorizontalWatching.setImage(R.drawable.view);
        holder.lTextViewHorizontalWatching.setBackground(R.drawable.bt_blue);

        holder.lTextViewHorizontalHeart.setImageSize(18);
        holder.lTextViewHorizontalHeart.setText(String.valueOf(String.valueOf(room.getReceivedHeart())));
        holder.lTextViewHorizontalHeart.setImage(R.drawable.love);
        holder.lTextViewHorizontalHeart.setBackground(R.drawable.bt_pink);

        holder.ivIsOnair.setVisibility(room.isOnAir() ? View.VISIBLE : View.INVISIBLE);

        if (room.getMode().equals(Constants.MODE_PUBLIC)) {
            holder.ivIsPrivate.setVisibility(View.GONE);
        } else {
            holder.ivIsPrivate.setVisibility(View.VISIBLE);
        }

        LLog.d(TAG, "position: " + position);
        if (position == roomList.size() - 1) {
            holder.viewSpace.setVisibility(View.VISIBLE);
        } else {
            holder.viewSpace.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != roomList ? roomList.size() : 0);
    }

    public class IdolRoomByCategoryDetailHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private SimpleDraweeView iv;
        private LTextViewHorizontal lTextViewHorizontalWatching;
        private LTextViewHorizontal lTextViewHorizontalHeart;
        private ImageView ivIsOnair;
        private ImageView ivIsPrivate;
        private View viewSpace;

        public IdolRoomByCategoryDetailHolder(View view) {
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.iv = (SimpleDraweeView) view.findViewById(R.id.iv);
            this.lTextViewHorizontalWatching = (LTextViewHorizontal) view.findViewById(R.id.l_text_view_watching);
            this.lTextViewHorizontalHeart = (LTextViewHorizontal) view.findViewById(R.id.l_text_view_heart);
            this.ivIsOnair = (ImageView) view.findViewById(R.id.iv_is_onair);
            this.ivIsPrivate = (ImageView) view.findViewById(R.id.iv_is_private);
            this.viewSpace = (View) view.findViewById(R.id.view_space);
        }
    }
}