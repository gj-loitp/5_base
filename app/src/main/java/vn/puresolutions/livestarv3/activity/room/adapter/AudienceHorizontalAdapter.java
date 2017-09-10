package vn.puresolutions.livestarv3.activity.room.adapter;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.User;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.ranking.DummyPerson;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

public class AudienceHorizontalAdapter extends RecyclerView.Adapter<AudienceHorizontalAdapter.BaseHolder> {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<User> lstUser;
    private Context context;
    private final int NUMBER_DISPLAY = 5;
    private int width;

    public interface Callback {
        public void onClick(User user);
    }

    private Callback callback;

    public AudienceHorizontalAdapter(Context context, ArrayList<User> mlstUser, Callback callback) {
        this.lstUser = mlstUser;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_audience_horizontal, null);
        width = viewGroup.getMeasuredWidth() / NUMBER_DISPLAY;
        //LLog.d(TAG, "width " + width);
        return new AudienceHolder(v);
    }

    @Override
    public void onBindViewHolder(BaseHolder baseHolder, int position) {
        if (baseHolder == null) {
            LLog.d(TAG, "onBindViewHolder baseHolder == null");
            return;
        }
        User user = lstUser.get(position);
        if (baseHolder instanceof AudienceHolder) {
            AudienceHolder audienceHolder = (AudienceHolder) baseHolder;
            LImageUtils.loadImage(audienceHolder.simpleDraweeView, user.getAvatarsPath().getAvatar());
            audienceHolder.simpleDraweeView.getLayoutParams().width = width;
            audienceHolder.viewGroupMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onClick(user);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != lstUser ? lstUser.size() : 0);
    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        public BaseHolder(View itemView) {
            super(itemView);
        }
    }

    public class AudienceHolder extends BaseHolder {
        private SimpleDraweeView simpleDraweeView;
        private RelativeLayout viewGroupMain;

        public AudienceHolder(View view) {
            super(view);
            this.simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.iv_avatar);
            this.viewGroupMain = (RelativeLayout) view.findViewById(R.id.view_group_main);
        }
    }
}