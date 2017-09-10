package vn.puresolutions.livestarv3.activity.onair.adapter;

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

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

public class IdolSquareRoomByCategoryAdapter extends RecyclerView.Adapter<IdolSquareRoomByCategoryAdapter.IdolRoomByCategoryDetailHolder> {
    private final String TAG = getClass().getSimpleName();
    private List<Room> roomList;
    private Activity activity;
    private Callback callback;
    //private int widthViewHolder;

    public interface Callback {
        public void onClick(Room room, int position, List<Room> roomList);
    }

    public IdolSquareRoomByCategoryAdapter(Activity activity, List<Room> roomList, Callback callback) {
        this.roomList = roomList;
        this.activity = activity;
        this.callback = callback;

        //int screenWidthInDp = (int) ScreenUtils.convertPixelsToDp((float) (ScreenUtils.getWidthScreen(activity) / 2), activity);
        //widthViewHolder = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, screenWidthInDp, activity.getResources().getDisplayMetrics());//200dp
    }

    @Override
    public IdolRoomByCategoryDetailHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_idol_on_air_square, null);
        //int width = (int) (viewGroup.getMeasuredWidth() / 4.5);
        //v.setMinimumWidth(width);
        int w = v.getWidth();
        LLog.d(TAG, "w: " + w);
        return new IdolRoomByCategoryDetailHolder(v);
    }

    @Override
    public void onBindViewHolder(IdolRoomByCategoryDetailHolder holder, int position) {
        Room room = roomList.get(position);
        LImageUtils.loadImage(holder.iv, room.getBanners().getW512h512());
        final String des = room.getTitle();
        holder.tvName.setText(des);
        LUIUtil.setMarquee(holder.tvName);
        LUIUtil.setTextShadow(holder.tvName);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(room, position, roomList);
                }
            }
        });
        holder.lTextViewHorizontalWatching.setImageSize(18);
        holder.lTextViewHorizontalWatching.setText(String.valueOf(room.getSession().getView()));
        holder.lTextViewHorizontalWatching.setImage(R.drawable.view);
        holder.lTextViewHorizontalWatching.setBackground(R.drawable.bt_blue);

        holder.lTextViewHorizontalHeart.setImageSize(18);
        holder.lTextViewHorizontalHeart.setText(String.valueOf(String.valueOf(room.getReceivedHeart())));
        holder.lTextViewHorizontalHeart.setImage(R.drawable.love);
        holder.lTextViewHorizontalHeart.setBackground(R.drawable.bt_pink);

        if (room.getMode().equals(Constants.MODE_PUBLIC)) {
            holder.ivIsPrivate.setVisibility(View.GONE);
        } else {
            holder.ivIsPrivate.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (null != roomList ? roomList.size() : 0);
    }

    public class IdolRoomByCategoryDetailHolder extends RecyclerView.ViewHolder {
        protected TextView tvName;
        protected SimpleDraweeView iv;
        protected LTextViewHorizontal lTextViewHorizontalWatching;
        protected LTextViewHorizontal lTextViewHorizontalHeart;
        protected ImageView ivIsPrivate;

        public IdolRoomByCategoryDetailHolder(View view) {
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.iv = (SimpleDraweeView) view.findViewById(R.id.iv);
            this.lTextViewHorizontalWatching = (LTextViewHorizontal) view.findViewById(R.id.l_text_view_watching);
            this.lTextViewHorizontalHeart = (LTextViewHorizontal) view.findViewById(R.id.l_text_view_heart);
            this.ivIsPrivate = (ImageView) view.findViewById(R.id.iv_is_private);
        }
    }
}