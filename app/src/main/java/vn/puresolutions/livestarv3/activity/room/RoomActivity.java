package vn.puresolutions.livestarv3.activity.room;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.view.LOnlyPageViewPager;

public class RoomActivity extends BaseActivity {
    private RoomViewPagerAdapter roomViewPagerAdapter;
    private LOnlyPageViewPager viewPager;
    private Room room;//current room
    private int position;//current position
    private List<Room> roomList = new ArrayList<>();
    private SimpleDraweeView ivBlurBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LUIUtil.setSoftInputMode(activity, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setCustomStatusBar(false);
        ButterKnife.bind(this);
        viewPager = (LOnlyPageViewPager) findViewById(R.id.view_pager);
        ivBlurBackground = (SimpleDraweeView) findViewById(R.id.iv_blur_background);

        //get data
        room = (Room) getIntent().getSerializableExtra(Constants.KEY_HOME_TO_ROOM);
        position = getIntent().getIntExtra(Constants.KEY_HOME_TO_ROOM_POSITION, Constants.NOT_FOUND);
        List<Room> tmpRoomList = (List<Room>) getIntent().getSerializableExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM);
        if (tmpRoomList == null || position == Constants.NOT_FOUND || tmpRoomList == null || tmpRoomList.isEmpty()) {
            AlertMessage.showError(activity, getString(R.string.err));
            return;
        }

        //remove any rooms which isOnAir=false
        //LLog.d(TAG, "before tmpRoomList: " + tmpRoomList.size());
        for (int i = 0; i < tmpRoomList.size(); i++) {
            if (tmpRoomList.get(i).isOnAir()) {
                //LLog.d(TAG, "add: " + tmpRoomList.get(i).getTitle() + ", " + tmpRoomList.get(i).getUser().getName());
                roomList.add(tmpRoomList.get(i));
            }
        }
        //LLog.d(TAG, "real list: " + roomList.size());

        //LLog.d(TAG, "room: " + LSApplication.getInstance().getGson().toJson(room));
        //LLog.d(TAG, "position: " + position);
        //LLog.d(TAG, "roomList: " + roomList.size());

        roomViewPagerAdapter = new RoomViewPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(0);//very important line
        viewPager.setAdapter(roomViewPagerAdapter);
        viewPager.setCurrentItem(position);//scroll to current page
        updateBackground(position);

        viewPager.setOnPageChangeListener(new LOnlyPageViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //LLog.d(TAG, "onPageScrolled position " + position);
            }

            @Override
            public void onPageSelected(int position) {
                LLog.d(TAG, "onPageSelected position " + position + ">>> " + roomList.get(position).getTitle());
                updateBackground(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //LLog.d(TAG, "onPageScrollStateChanged state " + state);
            }
        });
        AlertMessage.showSuccess(activity, getString(R.string.alert_watching_room));
    }

    private void updateBackground(int position) {
        LImageUtils.loadImage(ivBlurBackground, roomList.get(position).getBanners().getW330h330());
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
        return R.layout.activity_room_v3;
    }

    private class RoomViewPagerAdapter extends FragmentPagerAdapter {

        public RoomViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new FrmRoomV3Base();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.KEY_ROOM, roomList.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return roomList.size();
        }

        /*public int getItemPosition(Object object) {
            return POSITION_NONE;
        }*/
    }
}
