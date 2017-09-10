package vn.puresolutions.livestar.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.RoomDetail;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.RoomService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.helper.NotificationCenter;
import vn.puresolutions.livestar.helper.NotificationSocketHelper;
import vn.puresolutions.livestar.notification.handler.NotificationHandler;
import vn.puresolutions.livestarv3.utilities.old.ScreenUtils;
import vn.puresolutions.livestarv3.base.BaseActivity;

public class MainActivity extends BaseActivity implements NotificationCenter.NotificationCenterListener {
    @BindView(R.id.mainActivity_menu)
    View mainMenu;
    @BindView(R.id.mainMenu_imgLogout)
    ImageView imgSignOut;

    private NotificationSocketHelper notificationSocketHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mainMenu.setX(-ScreenUtils.getWidthScreen(this));
        mainMenu.setVisibility(View.GONE);
        mainMenu.setPadding(0, ScreenUtils.getStatusBarHeight(this), 0, 0);

        notificationSocketHelper = new NotificationSocketHelper(this);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.UserLoggedIn);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.UserLoggedOut);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.UserLevelChanged);

        NotificationCenter.getInstance().postNotificationName(NotificationCenter.EnterMainScreen);
        checkNotification(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkNotification(intent);
    }

    private void checkNotification(Intent intent) {
        if (intent.getBooleanExtra(NotificationHandler.BUNDLE_KEY_IS_FROM_NOTIFICATION, false)) {
            int roomId = intent.getIntExtra(NotificationHandler.BUNDLE_KEY_ROOM_ID, 0);
            RoomService service = RestClient.createService(RoomService.class);
            subscribe(service.getRoomDetail(roomId), new ApiSubscriber<RoomDetail>() {
                @Override
                public void onSuccess(RoomDetail roomDetail) {
                    // open room
                    Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                    intent.putExtra(RoomActivity.BUNDLE_KEY_ROOM, roomDetail);
                    startActivity(intent);
                }

                @Override
                public void onFail(Throwable e) {
                    handleException(e);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!notificationSocketHelper.isConnected() && UserInfo.isLoggedIn()) {
            notificationSocketHelper.open(UserInfo.getUserLoggedIn());
        }
    }

    @Override
    protected void onDestroy() {
        notificationSocketHelper.close();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.UserLoggedIn);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.UserLoggedOut);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.UserLevelChanged);
        super.onDestroy();
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.UserLoggedIn) {
            notificationSocketHelper.open(UserInfo.getUserLoggedIn());
        } else if (id == NotificationCenter.UserLoggedOut) {
            notificationSocketHelper.close();
        } else if (id == NotificationCenter.UserLevelChanged) {
            //TODO
//            LevelUpDialog dialog = new LevelUpDialog(this);
//            dialog.show();
//            dialog.setData((LevelChanging) args[0]);
        }
    }

    void signOut() {
        UserInfo.logout();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.mainActivity_imgMenu)
    void onToggleMenu() {
        float screenWidth = ScreenUtils.getWidthScreen(this);
        if (mainMenu.getX() < 0) {
            // check to show signIn/SignOut
//            if (UserInfo.isLoggedIn()) {
//                tvProfile.setText(R.string.profile);
//                imgProfile.setImageResource(R.drawable.ic_profile);
//                imgSignOut.setVisibility(View.VISIBLE);
//            } else {
//                tvProfile.setText(R.string.sign_in);
//                imgProfile.setImageResource(R.drawable.ic_sign_in);
//                imgSignOut.setVisibility(View.INVISIBLE);
//            }

            mainMenu.setVisibility(View.VISIBLE);
            moveView(mainMenu, -screenWidth, 0);
        } else {
            moveView(mainMenu, 0, -screenWidth);
        }
    }

    private void moveView(View paramView, float fromX, float toX) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView,
                "translationX", fromX, toX);
        localObjectAnimator.setDuration(300L);
        localObjectAnimator.start();
    }

    @OnClick({R.id.mainMenu_imgClose, R.id.mainMenu_onAir,
            R.id.mainMenu_about, R.id.mainMenu_title,
            R.id.mainMenu_purchase, R.id.mainMenu_imgLogout, R.id.mainMenu_ranking, R.id.mainMenu_search})
    public void onMenuItemClick(View view) {
        int tabId = 0;
        Intent intent;
        switch (view.getId()) {
            case R.id.mainMenu_onAir:
                onToggleMenu();
                break;

            case R.id.mainMenu_about:
                intent = new Intent(this, WebActivity.class);
                intent.putExtra(WebActivity.BUNDLE_KEY_TITLE, getString(R.string.about));
                intent.putExtra(WebActivity.BUNDLE_KEY_URL, getString(R.string.about_url));
                startActivity(intent);
                onToggleMenu();
                return;

            case R.id.mainMenu_purchase:
                onToggleMenu();
                if (UserInfo.getUserLoggedIn() == null) {
                    intent = new Intent(this, SignInActivity.class);
                    intent.putExtra(FacebookSignInActivity.BUNDLE_KEY_IS_FINISH_WHEN_COMPLETED, true);
                    startActivity(intent);
                    onToggleMenu();
                    return;
                }
                intent = new Intent(this, PurchaseActivity.class);
                startActivity(intent);
                return;
            case R.id.mainMenu_ranking:
                onToggleMenu();
                intent = new Intent(this, RankActivity.class);
                startActivity(intent);
                return;
            case R.id.mainMenu_search:
                onToggleMenu();
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return;
//            case R.id.mainMenu_search:
//                break;
//
//            case R.id.mainMenu_profile:
//                if (!UserInfo.isLoggedIn()) {
//                    intent = new Intent(this, SignInActivity.class);
//                    intent.putExtra(FacebookSignInActivity.BUNDLE_KEY_IS_FINISH_WHEN_COMPLETED, true);
//                    startActivity(intent);
//                    onToggleMenu();
//                    return;
//                }
//                break;

            case R.id.mainMenu_imgLogout:
                signOut();
                return;

            case R.id.mainMenu_title:
                // do nothing, but don't remove this case
                return;

            case R.id.mainMenu_imgClose:
                onToggleMenu();
                return;
        }

        //Toast.makeText(this, "Tính năng đang hoàn thiện", Toast.LENGTH_SHORT).show();
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
        return R.layout.activity_main;
    }
}
