package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.model.Room;
import vn.puresolutions.livestar.core.api.model.RoomDetail;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.RoomService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.fragment.RoomFragment;
import vn.puresolutions.livestar.helper.KeyboardHelper;
import vn.puresolutions.livestar.helper.TrackingHelper;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.base.BaseActivity;

/**
 * Created by khanh on 8/8/16.
 */
public class RoomActivity extends BaseActivity {
    public static final String BUNDLE_KEY_ROOM = "Room";

    @BindView(R.id.roomActivity_imgPoster)
    SimpleDraweeView imgPoster;

    @BindView(R.id.roomActivity_pgbLoading)
    ProgressBar pgbLoading;

    @BindView(R.id.roomActivity_fltContainer)
    FrameLayout fltContainer;

    private boolean isRoomOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        final Room room = (Room) getIntent().getSerializableExtra(BUNDLE_KEY_ROOM);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgPoster.setTransitionName(room.getTitle());
            getWindow().getEnterTransition().setDuration(500);
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP));
            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.CENTER_CROP));
        }
        //Log.i("RoomActivity","Poster: "+ room.getPoster());
        //Log.i("RoomActivity","Poster: "+ room.getThumb());

        try {
            if (!room.getPoster().equalsIgnoreCase(null) || room.getPoster().length() != 0) {
                LImageUtils.loadImage(imgPoster, room.getPoster());
            }
        } catch (Exception e) {
            LImageUtils.loadImage(imgPoster, room.getThumb());
            e.printStackTrace();
        }

        pgbLoading.postDelayed(() -> {
            pgbLoading.setVisibility(View.VISIBLE);
            loadRoom(room.getId());
        }, 500);

        KeyboardHelper.assistActivity(this);
    }

    private void loadRoom(int roomId) {
        RoomService service = RestClient.createService(RoomService.class);
        subscribe(service.getRoomDetail(roomId), new ApiSubscriber<RoomDetail>() {
            @Override
            public void onSuccess(RoomDetail room) {
                pgbLoading.setVisibility(View.GONE);
                if (room.isOnAir()) {
                    openRoom(room);

                    TrackingHelper.trackScreenName("Room " + room.getTitle());
                } else {
                    //TODO
                    Toast.makeText(RoomActivity.this, "Idol chưa online, quay lại sau nhé!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFail(Throwable e) {
                pgbLoading.setVisibility(View.GONE);
                handleException(e);
            }
        });

//        RoomDetail roomDetail = new RoomDetail();
//        Broadcaster broadcaster = new Broadcaster();
//        broadcaster.setName("Idol");
//        roomDetail.setBroadcaster(broadcaster);
//        openRoom(roomDetail);
    }

    private void openRoom(RoomDetail room) {
        imgPoster.setVisibility(View.INVISIBLE);
        isRoomOpened = true;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //ScreenUtils.hideSystemBars(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.roomActivity_fltContainer, RoomFragment.newInstance(room))
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if (isRoomOpened) {
            fltContainer.setVisibility(View.INVISIBLE);
            imgPoster.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            // close room
            TrackingHelper.trackScreenName(MainActivity.class);
        }
        super.onBackPressed();
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
        return R.layout.activity_room;
    }
}
