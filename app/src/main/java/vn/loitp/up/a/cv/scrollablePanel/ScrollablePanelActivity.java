package vn.loitp.up.a.cv.scrollablePanel;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.loitp.annotation.IsFullScreen;
import com.loitp.annotation.LogTag;
import com.loitp.core.base.BaseActivityFont;
import com.loitp.views.ab.LActionBar;
import com.loitp.views.scrollablePanel.LScrollablePanel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import vn.loitp.R;

//https://github.com/Kelin-Hong/ScrollablePanel?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5306
@LogTag("ScrollablePanelActivity")
@IsFullScreen(false)
public class ScrollablePanelActivity extends BaseActivityFont {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DAY_UI_MONTH_DAY_FORMAT = new SimpleDateFormat("MM-dd");
    public static final SimpleDateFormat WEEK_FORMAT = new SimpleDateFormat("EEE", Locale.US);

//    @Override
//    protected int setLayoutResourceId() {
//        return R.layout.a_scrollable_panel;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_scrollable_panel);
        setupViews();
    }

    private void setupViews() {
        final LActionBar lActionBar = findViewById(R.id.lActionBar);
        if (lActionBar != null) {
            if (lActionBar.getIvIconRight() != null) {
                lActionBar.getIvIconRight().setImageResource(R.color.transparent);
            }
            if (lActionBar.getTvTitle() != null) {
                lActionBar.getTvTitle().setText(getClass().getSimpleName());
            }
            Objects.requireNonNull(lActionBar.getIvIconLeft()).setOnClickListener(view -> onBackPressed());
        }
        final LScrollablePanel LScrollablePanel = findViewById(R.id.scrollablePanel);
        final ScrollablePanelAdapter scrollablePanelAdapter = new ScrollablePanelAdapter();
        generateTestData(scrollablePanelAdapter);
        LScrollablePanel.setPanelAdapter(scrollablePanelAdapter);
    }

    private void generateTestData(@NonNull final ScrollablePanelAdapter scrollablePanelAdapter) {
        final List<RoomInfo> roomInfoList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            final RoomInfo roomInfo = new RoomInfo();
            roomInfo.setRoomType("VIP");
            roomInfo.setRoomId(i);
            roomInfo.setRoomName("Name " + i);
            roomInfoList.add(roomInfo);
        }
        for (int i = 6; i < 30; i++) {
            final RoomInfo roomInfo = new RoomInfo();
            roomInfo.setRoomType("Standard");
            roomInfo.setRoomId(i);
            roomInfo.setRoomName("Name " + i);
            roomInfoList.add(roomInfo);
        }
        scrollablePanelAdapter.setRoomInfoList(roomInfoList);

        final List<DateInfo> dateInfoList = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 14; i++) {
            final DateInfo dateInfo = new DateInfo();
            final String date = DAY_UI_MONTH_DAY_FORMAT.format(calendar.getTime());
            final String week = WEEK_FORMAT.format(calendar.getTime());
            dateInfo.setDate(date);
            dateInfo.setWeek(week);
            dateInfoList.add(dateInfo);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        scrollablePanelAdapter.setDateInfoList(dateInfoList);

        final List<List<OrderInfo>> ordersList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            final List<OrderInfo> orderInfoList = new ArrayList<>();
            for (int j = 0; j < 14; j++) {
                final OrderInfo orderInfo = new OrderInfo();
                orderInfo.setGuestName("NO." + i + j);
                orderInfo.setBegin(true);
                orderInfo.setStatus(OrderInfo.Status.randomStatus());
                if (orderInfoList.size() > 0) {
                    final OrderInfo lastOrderInfo = orderInfoList.get(orderInfoList.size() - 1);
                    if (Objects.requireNonNull(orderInfo.getStatus()).ordinal() == Objects.requireNonNull(lastOrderInfo.getStatus()).ordinal()) {
                        orderInfo.setId(lastOrderInfo.getId());
                        orderInfo.setBegin(false);
                        orderInfo.setGuestName("Loitp");
                    } else {
                        if (new Random().nextBoolean()) {
                            orderInfo.setStatus(OrderInfo.Status.BLANK);
                        }
                    }
                }
                orderInfoList.add(orderInfo);
            }
            ordersList.add(orderInfoList);
        }
        scrollablePanelAdapter.setOrdersList(ordersList);
    }
}
