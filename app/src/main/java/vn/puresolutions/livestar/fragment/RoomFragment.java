package vn.puresolutions.livestar.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.lmk.videoplayer.VideoView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tyrantgit.widget.HeartLayout;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.activity.PurchaseActivity;
import vn.puresolutions.livestar.activity.RoomActivity;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.RoomDetail;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.model.param.ShareResponse;
import vn.puresolutions.livestar.core.api.model.param.SubmitShareParam;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.AccountService;
import vn.puresolutions.livestar.custom.dialog.ScheduleDialog;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.custom.view.ActionsView;
import vn.puresolutions.livestar.custom.view.AudiencesView;
import vn.puresolutions.livestar.custom.view.ChatView;
import vn.puresolutions.livestar.custom.view.GiftsView;
import vn.puresolutions.livestar.custom.view.IdolProfileView;
import vn.puresolutions.livestar.custom.view.LoungeView;
import vn.puresolutions.livestar.custom.view.RoomLeftMenuView;
import vn.puresolutions.livestar.helper.NotificationCenter;
import vn.puresolutions.livestar.helper.RoomViewManager;
import vn.puresolutions.livestar.room.IRoomView;
import vn.puresolutions.livestar.room.RoomCenter;
import vn.puresolutions.livestarv3.base.BaseFragment;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.old.DeviceUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.old.ViewUtils;

/**
 * Created by khanh on 8/8/16.
 */
public class RoomFragment extends BaseFragment implements IRoomView, NotificationCenter.NotificationCenterListener {

    private static final int DISMISS_TIMEOUT = 10 * 1000; // 10s

    @BindView(R.id.roomFragment_videoView)
    VideoView videoView;
    @BindView(R.id.roomFragment_imgGift)
    ImageView imgGift;
    @BindView(R.id.roomFragment_vwGift)
    GiftsView vwGift;
    @BindView(R.id.roomFragment_imgVote)
    ImageView imgVote;
    @BindView(R.id.roomFragment_imgMenu)
    ImageView imgMenu;
    @BindView(R.id.roomFragment_vwLeftMenu)
    RoomLeftMenuView vwLeftMenu;
    @BindView(R.id.roomFragment_vwAction)
    ActionsView vwAction;
    @BindView(R.id.roomFragment_imgLounge)
    ImageView imgLounge;
    @BindView(R.id.roomFragment_vwLounge)
    LoungeView vwLounge;
    @BindView(R.id.roomFragment_vwAudiences)
    AudiencesView vwAudiences;
    @BindView(R.id.roomFragment_heartLayout)
    HeartLayout heartLayout;
    @BindView(R.id.roomFragment_tvHeartNumber)
    TextView tvHeartNumber;
    @BindView(R.id.roomFragment_vwDismiss)
    View vwDismiss;
    @BindView(R.id.roomFragment_imgAvatar)
    SimpleDraweeView imgAvatar;
    @BindView(R.id.roomFragment_tvIdolName)
    TextView tvIdolName;
    @BindView(R.id.roomFragment_vwIdolProfile)
    IdolProfileView vwIdolProfile;
    @BindView(R.id.roomFragment_tvFollow)
    TextView tvFollow;
    @BindView(R.id.roomFragment_grpProfile)
    View grpProfile;
    @BindView(R.id.roomFragment_chatView)
    ChatView chatView;
    @BindViews({R.id.roomFragment_imgHeart, R.id.roomFragment_tvHeartNumber, R.id.roomFragment_imgGift,
    R.id.roomFragment_imgVote, R.id.roomFragment_imgLounge})
    List<View> bottomActionViews;
    @BindViews({R.id.roomFragment_tvFollow, R.id.roomFragment_imgShare, R.id.roomFragment_imgMenu,
            R.id.roomFragment_imgHide, R.id.roomFragment_imgAvatar, R.id.roomFragment_tvIdolName})
    List<View> topActionViews;

    private ScheduleDialog scheduleDialog;
    private Random random = new Random();
    private ViewGroup root;
    private RoomCenter roomCenter;
    private RoomViewManager viewManager;
    private RoomViewManager.Builder menuViewBuilder;
    private RoomViewManager.Builder audienceViewBuilder;
    private CallbackManager callbackManager;
    private String shareLink;
    public static RoomFragment newInstance(RoomDetail room) {
        Bundle args = new Bundle();
        args.putSerializable(RoomActivity.BUNDLE_KEY_ROOM, room);
        RoomFragment fragment = new RoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return root = (ViewGroup) inflater.inflate(R.layout.fragment_room, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        RoomDetail room = (RoomDetail) getArguments().getSerializable(RoomActivity.BUNDLE_KEY_ROOM);
        if (room != null) {
            roomCenter = RoomCenter.getInstance();
            roomCenter.onCreate(getActivity(), this, room);

            LImageUtils.loadImage(imgAvatar, room.getBroadcaster().getAvatar());
            tvIdolName.setText(room.getBroadcaster().getName());

            shareLink = String.format(getString(R.string.facebook_share_link_format), room.getSlug());
        }

        if (UserInfo.isLoggedIn()) {
            updateHeartNumber(UserInfo.getUserLoggedIn().getHeart());
        }
        tvFollow.setSelected(room.getBroadcaster().isFollow());
        tvFollow.setText(tvFollow.isSelected() ? getString(R.string.followed) : getString(R.string.follow));

        scheduleDialog = new ScheduleDialog(getContext());
        setupViewManager();

        // register notification
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.HeartReceivedNewMessage);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.UserReceiveHeart);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.UserLoggedIn);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FollowIdolSuccess);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FollowIdolFail);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.KeyboardShow);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.KeyboardHide);
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
        viewManager.onResume();
        chatView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    public void onDestroyView() {
        super.onStop();
        videoView.onDestroy();
        viewManager.onDestroy();
        roomCenter.onDestroy();
        chatView.onDestroy();
        // unregister notification
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.HeartReceivedNewMessage);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.UserReceiveHeart);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.UserLoggedIn);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FollowIdolSuccess);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FollowIdolFail);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.KeyboardShow);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.KeyboardHide);

        super.onDestroyView();
    }

    @Override
    public void playStream(String url) {
        videoView.play(url);
    }

    private void setupViewManager() {
        viewManager = new RoomViewManager(root, vwDismiss);

        // gift
        viewManager.addView(vwGift, imgGift)
                .activeImageResource(R.drawable.ic_gift_selected)
                .deActiveImageResource(R.drawable.ic_gift)
                .translateY().build();

        // vote
        viewManager.addView(vwAction, imgVote)
                .activeImageResource(R.drawable.ic_vote_selected)
                .deActiveImageResource(R.drawable.ic_vote)
                .translateX(RoomViewManager.REVERSE).build();

        // idol profile
        viewManager.addView(vwIdolProfile, imgAvatar)
                .translateX(RoomViewManager.FORWARD).build();

        // lounge
        viewManager.addView(vwLounge, imgLounge)
                .activeImageResource(R.drawable.ic_chair_selected)
                .deActiveImageResource(R.drawable.ic_chair)
                .translateX(RoomViewManager.REVERSE).build();
        // menu
        menuViewBuilder = viewManager.addView(vwLeftMenu, imgMenu)
                .activeImageResource(R.drawable.ic_view_more)
                .deActiveImageResource(R.drawable.ic_view_more)
                .translateX(RoomViewManager.REVERSE);
        menuViewBuilder.build();

        // audience
        audienceViewBuilder = viewManager.addView(vwAudiences, null)
                .translateX(RoomViewManager.REVERSE);
        audienceViewBuilder.build();

        // setup listener
        vwLeftMenu.setListener(new RoomLeftMenuView.LeftMenuListener() {
            @Override
            public void onShowAudiences() {
                menuViewBuilder.toggedRoomView(false);
                audienceViewBuilder.toggedRoomView(true);
            }

            @Override
            public void onShowCalendar() {
                menuViewBuilder.toggedRoomView(false);
                scheduleDialog.show();
            }

            @Override
            public void onPurchaseCoin() {
                if (UserInfo.checkLoginAndShowDialog(context)) {
                    Intent intent = new Intent(getActivity(), PurchaseActivity.class);
                    startActivity(intent);
                }
            }
        });

        viewManager.loadData();
    }

    @OnClick(R.id.roomFragment_imgHeart)
    void onSendHeart() {
        if (UserInfo.checkLoginAndShowDialog(context)) {
            User user = UserInfo.getUserLoggedIn();
            if (user.getHeart() > 0) {
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(25);

                roomCenter.sendHeart();

                user.setHeart(user.getHeart() - 1);
                UserInfo.saveUserLogged();

                updateHeartNumber(user.getHeart());
            } else {
                AlertMessage.showError(context, R.string.not_enough_heart);
            }
        }
    }

    @OnClick(R.id.roomFragment_imgShare)
    void shareFb() {
        callbackManager = CallbackManager.Factory.create();
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareDialog shareDialog = new ShareDialog(this);
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(shareLink))
                    .build();
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    Log.i("Sharing", "successfully ");
                    // Call api
                    AccountService service = RestClient.createService(AccountService.class);
                    SubmitShareParam param = new SubmitShareParam();
                    param.setDeviceId(DeviceUtils.getUniquePsuedoID());
                    param.setRoomId(RoomCenter.getInstance().roomId);
                   /* param.setPostId(result.getPostId());
                    param.setAccessToken(AccessToken.getCurrentAccessToken().getToken());
                    Log.d("RomFragment","AT -> " + AccessToken.getCurrentAccessToken().getToken());*/
                    subscribe(service.shareFB(param), new ApiSubscriber<ShareResponse>() {
                        @Override
                        public void onSuccess(ShareResponse result) {
                            UserInfo.getUserLoggedIn().setMoney(result.getUserMoney());
                            UserInfo.saveUserLogged();
                            Log.i("Sharing", "Granted point");
                        }

                        @Override
                        public void onFail(Throwable e) {
                            handleException(e);
                            //AlertMessage.showError(getContext(), R.string.already_shared_room);
                        }
                    });
                }

                @Override
                public void onCancel() {
                    Log.i("Sharing", "cancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.i("Sharing", "error: " + error.getMessage());
                }
            });
            shareDialog.show(linkContent);
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.roomFragment_tvFollow)
    void onFollow() {
        if (UserInfo.checkLoginAndShowDialog(context)) {
            tvFollow.setSelected(roomCenter.followIdol());
            tvFollow.setText(tvFollow.isSelected() ? getString(R.string.followed) : getString(R.string.follow));
        }
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.HeartReceivedNewMessage) {
            heartLayout.addHeart(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        } else if (id == NotificationCenter.UserReceiveHeart) {
            int heartNumber = (int) args[0];
            updateHeartNumber(heartNumber);
        } else if (id == NotificationCenter.UserLoggedIn) {
            updateHeartNumber(UserInfo.getUserLoggedIn().getHeart());
        } else if (id == NotificationCenter.FollowIdolSuccess) {
            if (((Boolean) args[0])) {
                //new FollowIdolDialog(getActivity()).show();
            }
        } else if (id == NotificationCenter.FollowIdolFail) {
            tvFollow.setSelected((Boolean) args[0]);
            tvFollow.setText(tvFollow.isSelected() ? getString(R.string.followed) : getString(R.string.follow));
        } else if (id == NotificationCenter.KeyboardShow) {
            viewManager.dismissViews();
            grpProfile.setVisibility(View.INVISIBLE);
            for (View view : bottomActionViews) {
                view.setVisibility(View.GONE);
            }
        } else if (id == NotificationCenter.KeyboardHide) {
            grpProfile.setVisibility(View.VISIBLE);
            for (View view : bottomActionViews) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateHeartNumber(int heartNumber) {
        tvHeartNumber.setText(String.valueOf(heartNumber));
    }

    @OnClick(R.id.roomFragment_imgHide)
    void onHideRoom() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.roomFragment_vwDismissControls)
    void onDismissControls() {
        if (chatView.getVisibility() == View.VISIBLE) {
            // hide all controls
            ViewUtils.fadeOut(chatView, 300);
            for (View item: bottomActionViews) {
                ViewUtils.fadeOut(item, 300);
            }
            for (View item: topActionViews) {
                ViewUtils.fadeOut(item, 300);
            }

            // hide status bar
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {
            // show all controls
            ViewUtils.fadeIn(chatView, 300);
            for (View view: bottomActionViews) {
                ViewUtils.fadeIn(view, 300);
            }
            for (View item: topActionViews) {
                ViewUtils.fadeIn(item, 300);
            }

            // Show status bar
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}
