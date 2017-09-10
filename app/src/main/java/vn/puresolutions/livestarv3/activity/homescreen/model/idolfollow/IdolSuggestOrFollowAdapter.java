package vn.puresolutions.livestarv3.activity.homescreen.model.idolfollow;

/**
 * Created by loitp
 */

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestarv3.utilities.old.ScreenUtils;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;

public class IdolSuggestOrFollowAdapter extends RecyclerView.Adapter<IdolSuggestOrFollowAdapter.IdolFollowDetailHolder> {
    private List<Room> roomList;
    private Activity activity;
    private Callback callback;
    private int widthViewHolder;

    public interface Callback {
        public void onClickRootView(Room room);

        public void onClickFollow(Room room);
    }

    public IdolSuggestOrFollowAdapter(Activity activity, List<Room> roomList, Callback callback) {
        this.roomList = roomList;
        this.activity = activity;
        this.callback = callback;

        int screenWidthInDp = (int) ScreenUtils.convertPixelsToDp((float) (ScreenUtils.getWidthScreen(activity) / 2.5), activity);
        widthViewHolder = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, screenWidthInDp, activity.getResources().getDisplayMetrics());//200dp
    }

    @Override
    public IdolFollowDetailHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_idol_follow_detail, null);
        //int width = (int) (viewGroup.getMeasuredWidth() / 2.5);
        //v.setMinimumWidth(width);
        return new IdolFollowDetailHolder(v);
    }

    @Override
    public void onBindViewHolder(IdolFollowDetailHolder holder, int position) {
        Room room = roomList.get(position);

        boolean isOnAir = room.isOnAir();
        boolean isFollow = room.isIsFollow();

        holder.tvName.setText(room.getTitle());
        LUIUtil.setMarquee(holder.tvName);

        holder.lTextFollow.setImageSize(10);
        if (isFollow) {
            holder.lTextFollow.setText(activity.getString(R.string.followed));
            holder.lTextFollow.setBackground(R.drawable.bt_followed);
            holder.lTextFollow.setImage(R.drawable.tick);
        } else {
            holder.lTextFollow.setText(activity.getString(R.string.follow));
            holder.lTextFollow.setBackground(R.drawable.bt_follow);
            holder.lTextFollow.setImage(R.drawable.ic_add_white_48dp);
        }

        holder.lTextFollowNumber.setImageSize(10);
        holder.lTextFollowNumber.setText(String.valueOf(room.getFollow()));
        holder.lTextFollowNumber.getTvDescription().setTextColor(ContextCompat.getColor(activity, R.color.Black));
        holder.lTextFollowNumber.setImage(R.drawable.follow);
        holder.lTextFollowNumber.setBackground(R.color.transparent);

        if (room.getBanners() != null && room.getBanner() != null) {
            LImageUtils.loadImage(holder.avatar, room.getBanners().getW330h330());
        }

        holder.lTextFollow.setOnItemClick(new LTextViewHorizontal.Callback() {
            @Override
            public void OnClickItem() {
                LAnimationUtil.play(holder.lTextFollow, Techniques.Flash);
                if (callback != null) {
                    callback.onClickFollow(room);
                }
            }
        });
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LAnimationUtil.play(holder.llMain, Techniques.Pulse, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        if (callback != null) {
                            callback.onClickRootView(room);
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
    }

    @Override
    public int getItemCount() {
        return (null != roomList ? roomList.size() : 0);
    }

    public class IdolFollowDetailHolder extends RecyclerView.ViewHolder {
        protected LinearLayout llMain;
        protected TextView tvName;
        protected SimpleDraweeView avatar;
        protected LTextViewHorizontal lTextFollow;
        protected LTextViewHorizontal lTextFollowNumber;

        public IdolFollowDetailHolder(View view) {
            super(view);
            this.llMain = (LinearLayout) view.findViewById(R.id.ll_main);
            ViewGroup.LayoutParams params = llMain.getLayoutParams();
            //params.height = widthViewHolder;
            params.width = widthViewHolder;
            llMain.setLayoutParams(params);

            this.tvName = (TextView) view.findViewById(R.id.tvTitle);
            this.avatar = (SimpleDraweeView) view.findViewById(R.id.iv_avatar);
            this.lTextFollow = (LTextViewHorizontal) view.findViewById(R.id.l_text_follow);
            this.lTextFollowNumber = (LTextViewHorizontal) view.findViewById(R.id.l_text_follow_number);
        }

        public TextView getTvName() {
            return tvName;
        }

        public LTextViewHorizontal getlTextFollow() {
            return lTextFollow;
        }
    }
}