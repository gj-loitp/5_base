package vn.puresolutions.livestarv3.activity.schedule.calendar;

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

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.ranking.DummyPerson;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.BaseHolder> {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<DummyPerson> dummyPersonArrayList;
    private Activity activity;
    private Callback callback;

    public interface Callback {
        public void onClickStateFollow(DummyPerson dummyPerson);

        public void onClickEvent(DummyPerson dummyPerson);
    }

    public ScheduleAdapter(Activity activity, ArrayList<DummyPerson> dummyPersonArrayList, Callback callback) {
        this.dummyPersonArrayList = dummyPersonArrayList;
        this.activity = activity;
        this.callback = callback;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_schedule_page, null);
        return new ScheduleHolder(v);
    }

    @Override
    public void onBindViewHolder(BaseHolder baseHolder, int position) {
        if (baseHolder == null) {
            LLog.d(TAG, "onBindViewHolder baseHolder == null");
            return;
        }
        DummyPerson dummyPerson = dummyPersonArrayList.get(position);
        if (baseHolder instanceof ScheduleHolder) {
            ScheduleHolder scheduleHolder = (ScheduleHolder) baseHolder;
            LImageUtils.loadImage(scheduleHolder.ivAvatar, dummyPerson.getImage());
            scheduleHolder.tvAmPm.setText("AM");
            scheduleHolder.tvHour.setText("69:69");
            scheduleHolder.tvNameIdol.setText(dummyPerson.getName());
            scheduleHolder.tvFollowState.setText("+ Theo doi");
            scheduleHolder.tvFollowState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onClickStateFollow(dummyPerson);
                    }
                }
            });
            scheduleHolder.ivEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onClickEvent(dummyPerson);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != dummyPersonArrayList ? dummyPersonArrayList.size() : 0);
    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        public BaseHolder(View itemView) {
            super(itemView);
        }
    }

    public class ScheduleHolder extends BaseHolder {
        private SimpleDraweeView ivAvatar;
        private TextView tvHour;
        private TextView tvAmPm;
        private TextView tvNameIdol;
        private TextView tvFollowState;
        private ImageView ivEvent;

        public ScheduleHolder(View view) {
            super(view);
            this.ivAvatar = (SimpleDraweeView) view.findViewById(R.id.iv_avatar);
            this.tvHour = (TextView) view.findViewById(R.id.tv_hour);
            this.tvAmPm = (TextView) view.findViewById(R.id.tv_am_pm);
            this.tvNameIdol = (TextView) view.findViewById(R.id.tv_name_idol);
            this.tvFollowState = (TextView) view.findViewById(R.id.tv_follow_state);
            this.ivEvent = (ImageView) view.findViewById(R.id.iv_event);
        }
    }
}