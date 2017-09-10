package vn.puresolutions.livestarv3.activity.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.view.LActionBar;

public class NotificationActivity extends BaseActivity {
    private ArrayList<Notification> lstNotification;
    @BindView(R.id.labNotifyScreen_labHeader)
    LActionBar labHeader;
    @BindView(R.id.rvNotifyScreen_Notify)
    RecyclerView rvNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        labHeader.setTvTitle(String.format(getString(R.string.notifiscreen_notify),10));
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {

            }
        });
        createDummyData();
        rvNotification.setHasFixedSize(true);
        rvNotification.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        NotificationAdapter notificationAdapter = new NotificationAdapter(this, lstNotification, new NotificationAdapter.Callback() {
            @Override
            public void onClick(View v) {
                int itemPosition = rvNotification.getChildLayoutPosition(v);
                Toast.makeText(NotificationActivity.this, String.valueOf(itemPosition), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NotificationActivity.this,NotificationContentActivity.class);
                intent.putExtra("Notification",lstNotification.get(itemPosition));
                startActivity(intent);
            }
        });
        rvNotification.setAdapter(notificationAdapter);
    }

    public void createDummyData() {
        lstNotification = new ArrayList<Notification>();
        for (int i = 1; i <= 10; i++) {
            Notification nt = new Notification("Thông báo từ idol",
                    "Jun jun bạn đang theo dõi đã cập nhật lịch diễn mới, click ngay để xem",
                    "Thông báo từ jun jun idol",
                    "Jun jun mà bạn đã follow sẽ livestream vào lúc 20:39 ngày 02/08 với nhiều tiết mục vô cùng dặc sắc. Bạn dành thời gian đón xem nhé",
                    "http://api.livestar.vn//uploads/user/avatar_crop/80751/avatar_crop.jpg",
                    "22/22");
            lstNotification.add(nt);
        }
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_notification;
    }
}
