package vn.puresolutions.livestarv3.activity.homescreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.buyticket.BuyTicket;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.login.LoginActivity;
import vn.puresolutions.livestarv3.activity.room.RoomActivity;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.view.LActionBar;

public class GoToPrivateRoomActivity extends BaseActivity {
    private LActionBar lActionBar;
    private int position;//current position
    private Room mRoom;
    private List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoom = (Room) getIntent().getSerializableExtra(Constants.KEY_HOME_TO_PRIVATE_ROOM);
        position = getIntent().getIntExtra(Constants.KEY_HOME_TO_ROOM_POSITION, Constants.NOT_FOUND);
        roomList = (List<Room>) getIntent().getSerializableExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM);
        if (mRoom == null || position == Constants.NOT_FOUND || roomList == null || roomList.isEmpty()) {
            AlertMessage.showError(activity, getString(R.string.err));
            onBackPressed();
        }
        findViews();
        setupActionBar();
        findViewById(R.id.tv_watch_prev_live).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTvWatchPrevLive();
            }
        });
        findViewById(R.id.tv_buy_ticket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTvBuyTicket();
            }
        });

        SimpleDraweeView ivAvatar = (SimpleDraweeView) findViewById(R.id.iv_avatar);
        TextView tvWatchCount = (TextView) findViewById(R.id.tv_watch_count);
        TextView tvLikeCount = (TextView) findViewById(R.id.tv_like_count);

        LImageUtils.loadImage(ivAvatar, mRoom.getBanners().getBanner());
        //TODO set watch count
        if (mRoom.getSession() != null) {
            tvWatchCount.setText(String.valueOf(mRoom.getSession().getView()));
        }
        tvLikeCount.setText(String.valueOf(mRoom.getReceivedHeart()));

        LUIUtil.setMarquee(tvWatchCount);
        LUIUtil.setMarquee(tvLikeCount);
    }

    private void setupActionBar() {
        lActionBar.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                //do nothing
            }
        });
        lActionBar.setImageBackIcon(R.drawable.back_white);
        lActionBar.setTvTitle("");
        lActionBar.hideMenuIcon();
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
        return R.layout.activity_go_to_private_room;
    }

    private void findViews() {
        lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LUIUtil.transActivityFadeIn(activity);
    }

    private void onClickTvWatchPrevLive() {
        AlertMessage.showSuccess(activity, "TODO onClickTvWatchPrevLive");
    }

    private void onClickTvBuyTicket() {
        if (LPref.isUserLoggedIn(activity)) {
            UserProfile userProfile = LPref.getUser(activity);
            if (userProfile.getMoney() >= Constants.TICKET_PRIVATE_ROOM_PRICE) {
                buyTicket();
            } else {
                Intent intent = new Intent(activity, GoToPrivateRoomNoCoinActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        } else {
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
            LUIUtil.transActivityFadeIn(activity);
        }
    }

    private void buyTicket() {
        final Dialog dialog = LDialogUtils.loadingMultiColor(activity, true);
        LDialogUtils.showDialog(dialog);
        LSService service = RestClient.createService(LSService.class);
        String roomId = mRoom.getId();
        String sessionId = mRoom.getSession().getId();
        LLog.d(TAG, "roomId " + roomId + ", sessionId " + sessionId);
        subscribe(service.buyTicket(roomId, sessionId), new ApiSubscriber<BuyTicket>() {
            @Override
            public void onSuccess(BuyTicket buyTicket) {
                if (buyTicket == null) {
                    AlertMessage.showError(activity, getString(R.string.err_buy_ticket));
                } else {
                    LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(buyTicket));
                    AlertMessage.showSuccess(activity, buyTicket.getMessage());

                    UserProfile userProfile = LPref.getUser(activity);
                    userProfile.setMoney((int) buyTicket.getRemainMoney());
                    LPref.setUser(activity, userProfile);

                    LPref.addBoughtSessionIdToList(activity, sessionId);

                    Intent intent = new Intent(activity, RoomActivity.class);
                    intent.putExtra(Constants.KEY_HOME_TO_ROOM, mRoom);
                    intent.putExtra(Constants.KEY_HOME_TO_ROOM_POSITION, position);
                    intent.putExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM, (Serializable) roomList);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(activity);
                    finish();
                }
                LDialogUtils.hideDialog(dialog);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                LDialogUtils.hideDialog(dialog);
            }
        });
    }
}
