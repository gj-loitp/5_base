package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.AudienceAdapter;
import vn.puresolutions.livestar.core.api.model.Users;
import vn.puresolutions.livestar.decoration.ActionItemDecoration;
import vn.puresolutions.livestar.helper.NotificationCenter;
import vn.puresolutions.livestar.room.RoomCenter;

/**
 * @author hoangphu
 * @since 9/22/16
 */
public class AudiencesView extends IRoomView implements NotificationCenter.NotificationCenterListener {

    @BindView(R.id.viewAudiences_rclAudiences)
    RecyclerView rclAudiences;
    @BindView(R.id.viewAudiences_tvNoData)
    TextView tvNoData;

    private AudienceAdapter adapter;

    public AudiencesView(Context context) {
        super(context);
        init(context);
    }

    public AudiencesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AudiencesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_audiences, this);
        ButterKnife.bind(this);

        adapter = new AudienceAdapter();
        LinearLayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rclAudiences.addItemDecoration(new ActionItemDecoration(context, R.dimen.action_recycler_item_padding));
        rclAudiences.setLayoutManager(layout);
        rclAudiences.setHasFixedSize(true);
        rclAudiences.setAdapter(adapter);

        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.addObserver(this, NotificationCenter.UserList);
        notificationCenter.addObserver(this, NotificationCenter.UserNew);
        notificationCenter.addObserver(this, NotificationCenter.UserLeave);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.removeObserver(this, NotificationCenter.UserList);
        notificationCenter.removeObserver(this, NotificationCenter.UserNew);
        notificationCenter.removeObserver(this, NotificationCenter.UserLeave);
    }

    @Override
    public void loadData() {
        fillData(RoomCenter.getInstance().users);
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        fillData(RoomCenter.getInstance().users);
    }

    private void fillData(Users user) {
        tvNoData.setVisibility(user.size() > 0 ? View.GONE : View.VISIBLE);
        if (user.size() > 0) {
            adapter.setItemsNtf(user);
        }
    }
}
