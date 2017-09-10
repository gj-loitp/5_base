package vn.puresolutions.livestarv3.activity.room;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.lmk.videoplayer.VideoView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import tyrantgit.widget.HeartLayout;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.CommonResponse;
import vn.puresolutions.livestar.corev3.api.model.v3.audiences.Audiences;
import vn.puresolutions.livestar.corev3.api.model.v3.followidol.FollowIdol;
import vn.puresolutions.livestar.corev3.api.model.v3.joinroom.JoinRoom;
import vn.puresolutions.livestar.corev3.api.model.v3.listgift.Item;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.model.v3.roomfindbyID.RoomFindByID;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.User;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestar.corev3.api.model.v3.sendgift.SendGift;
import vn.puresolutions.livestar.corev3.api.model.v3.sendheart.SendHeart;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.bank.getcoin.newdesign.GetCoinPagerActivity;
import vn.puresolutions.livestarv3.activity.livestream.model.ChatMessage;
import vn.puresolutions.livestarv3.activity.login.LoginActivity;
import vn.puresolutions.livestarv3.activity.userprofile.LiveUserProfileActivity;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LHomeUtil;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LStoreUtil;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.utilities.v3.RoomSocketHelper;
import vn.puresolutions.livestarv3.view.LBottomRoom;
import vn.puresolutions.livestarv3.view.LChatView;
import vn.puresolutions.livestarv3.view.LTopRoom;

/**
 * Created by www.muathu@gmail.com on 8/1/2017.
 */

//TODO tai sao khi nhan vao chat -> bi mat banner?

public class FrmRoomV3Public extends BaseFragmentForLOnlyViewPager implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private LTopRoom lTopRoom;
    private LBottomRoom lBottomRoom;
    private LChatView lChatView;
    private VideoView videoView;
    private TextView tvMsg;
    private RelativeLayout viewGroupMain;
    private boolean isFullScreenView;
    private CallbackManager callbackManager;
    private boolean isStateViewControllerLBottomView = true;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private Room mRoom;
    private RoomFindByID mRoomFindByID;
    private ImageView ivWait;
    private RelativeLayout rootView;
    private UserProfile mUserProfile;
    private ArrayList<User> lstUser = new ArrayList<>();
    private int page = 1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // LUIUtil.setSoftInputMode(getActivity(), WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
    }

    private void findViews(View view) {
        viewGroupMain = (RelativeLayout) view.findViewById(R.id.view_group_main);
        viewGroupMain.setOnClickListener(this);
        lTopRoom = (LTopRoom) view.findViewById(R.id.l_top_room);
        lBottomRoom = (LBottomRoom) view.findViewById(R.id.l_bottom_room);
        lChatView = (LChatView) view.findViewById(R.id.l_chat_view);
        playAnimFirst();
        videoView = (VideoView) view.findViewById(R.id.roomFragment_videoView);
        avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        ivWait = (ImageView) view.findViewById(R.id.iv_wait);
        rootView = (RelativeLayout) view.findViewById(R.id.root_view);
        tvMsg = (TextView) view.findViewById(R.id.tv_msg);
    }

    private void getRoomInfo() {
        //LLog.d(TAG, "->>>>>>>>>>>>>>>>>>>>.getRoomInfo mRoom.getId() " + mRoom.getId());
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.roomFindByID(mRoom.getId()), new ApiSubscriber<RoomFindByID>() {
            @Override
            public void onSuccess(RoomFindByID roomFindByID) {
                if (roomFindByID == null) {
                    //LLog.d(TAG, "getRoomInfo result == null");
                    AlertMessage.showError(getActivity(), R.string.err);
                } else {
                    //LLog.d(TAG, "getRoomInfo result != null");
                    mRoomFindByID = roomFindByID;
                    if (roomFindByID.getSession() == null) {
                        showViewWatchLivestreamFailed();
                        return;
                    }

                    LLog.d(TAG, ">>>>>>roomID: " + roomFindByID.getId() + ", userID: " + roomFindByID.getUser().getId() + ", sessionID: " + roomFindByID.getSession().getId());
                    RoomSocketHelper.getInstance().setCallback(new RoomSocketHelper.Callback() {
                        @Override
                        public void onNewComment(ChatMessage message) {
                            LLog.d(TAG, "onNewComment" + new Gson().toJson(message));
                            if (lChatView != null) {
                                lChatView.addMessage(message);
                            }
                        }

                        @Override
                        public void onNewConnectUser(ChatMessage message) {
                            lTopRoom.addItemFirst(message.getUser());
                            lTopRoom.setTvAudiences();
                        }

                        @Override
                        public void onDisconnect(ChatMessage message) {
                            LLog.d(TAG, "onDisconnect->remove");
                            lTopRoom.removeItem(message.getUser());
                            lTopRoom.setTvAudiences();
                        }

                        @Override
                        public void onSendHeart(ChatMessage message) {
                            //TODO
                        }

                        @Override
                        public void onSendGift(ChatMessage message) {
                            //TODO
                        }
                    });
                    initTopView();
                    initBottomView();
                    joinRoom();
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void showViewWatchLivestreamFailed() {
        lTopRoom.hideAllView();
        lBottomRoom.hideAllView();
        avLoadingIndicatorView.smoothToHide();
        tvMsg.setVisibility(View.VISIBLE);
        tvMsg.setText(mRoomFindByID.getTitle() + " hiện không livestream. Xin vui lòng chuyển sang phòng khác");
        LUIUtil.setTextShadow(tvMsg);
        tvMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing
                //cannot remove this function
            }
        });
    }

    private String linkPlay;

    private void joinRoom() {
        LSService service = RestClient.createService(LSService.class);
        String roomId = mRoomFindByID.getId();
        String sessionId = mRoomFindByID.getSession().getId();
        String userId = mRoomFindByID.getUser().getId();
        subscribe(service.joinRoom(roomId, sessionId, userId), new ApiSubscriber<JoinRoom>() {
            @Override
            public void onSuccess(JoinRoom joinRoom) {
                //LLog.d(TAG, "onSuccess joinRoom: " + LSApplication.getInstance().getGson().toJson(joinRoom));
                linkPlay = joinRoom.getLinkPlayLive();
                videoView.play(linkPlay);
                RoomSocketHelper.getInstance().open(roomId, LPref.getToken(getActivity()), new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        LLog.d(TAG, "connected");
                        //startLivestream();
                        //createConnection();
                        getAudiences();
                    }
                });

            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_room_v3_public, container, false);
        findViews(view);
        Bundle bundle = getArguments();
        if (bundle == null) {
            AlertMessage.showError(getActivity(), R.string.err);
            return view;
        }
        mRoom = (Room) bundle.getSerializable(Constants.KEY_ROOM);
        if (mRoom == null) {
            AlertMessage.showError(getActivity(), R.string.err);
            return view;
        }

        mUserProfile = LPref.getUser(getActivity());

        LLog.d(TAG, "onCreateView mRoom getTitle " + mRoom.getTitle() + ", getSlug: " + mRoom.getSlug() + ", getId " + mRoom.getId());
        getRoomInfo();

        videoView.setOnVideoViewListener(new VideoView.OnVideoViewListener() {
            @Override
            public void onPreparing() {
                LLog.d(TAG, "videoView onPreparing");
            }

            @Override
            public void onError(Exception e) {
                LLog.d(TAG, "videoView onError " + e.toString());
                showViewWatchLivestreamFailed();
                /*LUIUtil.setDelay(3000, new LUIUtil.DelayCallback() {
                    @Override
                    public void doAfter(int mls) {
                        videoView.play(linkPlay);
                    }
                });*/
            }

            @Override
            public void onEnded() {
                LLog.d(TAG, "videoView onEnded");
            }

            @Override
            public void onIdle() {
                LLog.d(TAG, "videoView onIdle");
            }

            @Override
            public void onBuffering() {
                LLog.d(TAG, "videoView onBuffering");
            }

            @Override
            public void onReady() {
                LLog.d(TAG, "videoView onReady");
                avLoadingIndicatorView.smoothToHide();
                LAnimationUtil.play(ivWait, Techniques.FadeOut, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        ivWait.setVisibility(View.GONE);
                        rootView.removeView(ivWait);
                    }

                    @Override
                    public void onRepeat() {
                        //do nothing
                    }

                    @Override
                    public void onStart() {
                        //do nothing
                    }
                });
            }
        });
        return view;
    }

    private void initTopView() {
        //LLog.d(TAG, ">initTopView");
        lTopRoom.setData(mRoomFindByID);
        lTopRoom.setCallback(new LTopRoom.Callback() {
            @Override
            public void onClickBack() {
                getActivity().onBackPressed();
            }

            @Override
            public void onClickFollow() {
                if (!LPref.isUserLoggedIn(getActivity())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(getActivity());
                } else {
                    if (mRoomFindByID.isIsFollow()) {
                        unfollowIdol(mRoomFindByID.getId());
                    } else {
                        followIdol(mRoomFindByID.getId());
                    }
                }
            }

            @Override
            public void onClickUserHorizontal(User user) {
                //TODO
                AlertMessage.showSuccess(getActivity(), "onClickUserHorizontal " + user.getName());
                //checkToClickFollowOrUnfollow(user);
            }

            @Override
            public void onClickUserVertical(User user) {
                Intent intent = new Intent(getActivity(), LiveUserProfileActivity.class);
                intent.putExtra(Constants.KEY_USER_TO_PROFILE, user.getId());
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }
        });
    }

    /*private void checkToClickFollowOrUnfollow(User user) {
        if (user == null) {
            return;
        }
        //LLog.d(TAG, "checkToClickFollowOrUnfollow " + LSApplication.getInstance().getGson().toJson(user));
        if (!LPref.isUserLoggedIn(getActivity())) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            LUIUtil.transActivityLeftToRightAniamtion(getActivity());
        } else {
            if (user.getRoom() == null || user.getRoom().getId() == null) {
                LLog.d(TAG, "null data >.<");
                return;
            }
            String roomID = user.getRoom().getId();
            if (mRoomFindByID.isIsFollow()) {
                //LLog.d(TAG, ">>>roomID " + roomID + ", ->unfollowIdol");
                unfollowIdol(roomID);
            } else {
                //LLog.d(TAG, ">>>roomID " + roomID + ", ->followIdol");
                followIdol(roomID);
            }
        }
    }*/

    private void initBottomView() {
        lBottomRoom.setData(mRoomFindByID);
        lBottomRoom.setStateCallback(new LBottomRoom.StateCallback() {
            @Override
            public void onStateViewChange(String stateView) {
                //LLog.d(TAG, "onStateViewChange " + stateView);
                isStateViewControllerLBottomView = stateView.equals(LBottomRoom.STATE_VIEW_CONTROLLER);
                //LLog.d(TAG, ">>>isStateViewControllerLBottomView " + isStateViewControllerLBottomView);
            }
        });
        lBottomRoom.setCallbackOnClick(new LBottomRoom.CallbackOnClick() {
            @Override
            public void onClickHeartButton(int posX, int posY, int widthHeartButton, int heightHeartButton) {
                if (!LPref.isUserLoggedIn(getActivity())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(getActivity());
                } else {
                    sendHeart(posX, posY, widthHeartButton, heightHeartButton);
                }
            }
        });
        lBottomRoom.setCallbackGift(new LBottomRoom.CallbackGift() {
            @Override
            public void onClickGift(Item item) {
                if (!LPref.isUserLoggedIn(getActivity())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(getActivity());
                } else {
                    if (mUserProfile.getMoney() >= item.getPrice()) {
                        //LLog.d(TAG, "sendGift " + item.getPrice());
                        if (isReadyToCallAPISendGift) {
                            sendGift(item);
                        }
                    } else {
                        LDialogUtils.showDialogNoCoin(getActivity(), item, new LDialogUtils.CallbackDialogNoCoin() {
                            @Override
                            public void onCancel() {
                                //do nothing
                            }

                            @Override
                            public void onGetCoin() {
                                Intent intent = new Intent(getActivity(), GetCoinPagerActivity.class);
                                startActivity(intent);
                                LUIUtil.transActivityFadeIn(getActivity());
                            }
                        });
                    }
                }
            }
        });
        lBottomRoom.setOnChatSend(new LBottomRoom.OnChatSend() {
            @Override
            public void OnSend(String text) {
                LLog.d(TAG, "Text:" + text);
                if (!LPref.isUserLoggedIn(getActivity())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(getActivity());
                } else {
                    RoomSocketHelper.getInstance().sendChat(getActivity(), text);
                }
            }
        });
        lBottomRoom.setViewMoreListener(new LBottomRoom.ViewMoreListener() {
            @Override
            public void viewMoreShareFb() {
                shareFb();
            }

            @Override
            public void viewMoreSchedule() {

            }
        });
    }

    private void shareFb() {
        callbackManager = CallbackManager.Factory.create();
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareDialog shareDialog = new ShareDialog(this);
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("google.com"))
                    .build();
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    LLog.d(TAG, "FB Share " + result.getPostId());
                    String postid = result.getPostId();
                    //lBottomRoom.updateUIButtonFacebook();
                    AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    LLog.d(TAG, "FB Share " + accessToken.getToken());
                    LSService service = RestClient.createService(LSService.class);
                    subscribe(service.shareFB(accessToken.getToken(),mRoomFindByID.getId()), new ApiSubscriber<CommonResponse>() {
                        @Override
                        public void onSuccess(CommonResponse result) {
                            /*if (result.getError() == null) {
                                LLog.d(TAG, "Share success");
                                AlertMessage.showSuccess(getActivity(), "Bạn đã share thành công, nhận được 10 EXP");
                            }*/
                            AlertMessage.showSuccess(getActivity(), result.getMessage());
                        }

                        @Override
                        public void onFail(Throwable e) {
                            handleException(e);
                        }
                    });

                }

                @Override
                public void onCancel() {
                    //isShareFB = false;
                    LLog.d(TAG, "cancel");
                }

                @Override
                public void onError(FacebookException error) {
                    //isShareFB = false;
                    LLog.d(TAG, "error: " + error.getMessage());
                }
            });
            shareDialog.show(linkContent);
        }
    }

    private boolean isReadyToCallAPISendGift = true;

    private void sendGift(Item item) {
        //LLog.d(TAG, "GiftsAdapter onClick " + item.getId() + ", mRoomFindByID.getUserId() " + mRoomFindByID.getUserId() + ",mRoomFindByID.getId() " + mRoomFindByID.getId() + ", mRoomFindByID.getUser().getId() " + mRoomFindByID.getUser().getId());
        LSService service = RestClient.createService(LSService.class);
        isReadyToCallAPISendGift = false;
        subscribe(service.sendGift(mRoomFindByID.getUserId(), item.getId(), 1), new ApiSubscriber<SendGift>() {
            @Override
            public void onSuccess(SendGift sendGift) {
                if (sendGift != null) {
                    //LLog.d(TAG, "sendGift onSuccess " + LSApplication.getInstance().getGson().toJson(sendGift));
                    AlertMessage.showSuccess(getActivity(), "Bạn đã tặng cho " + mRoom.getTitle() + " một " + item.getName() + ". Số xu còn lại là " + sendGift.getRemainMoney());
                    mUserProfile.setMoney(sendGift.getRemainMoney());
                    //LLog.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>onSuccess remain " + mUserProfile.getMoney());
                } else {
                    AlertMessage.showError(getActivity(), R.string.err_send_giif);
                }
                isReadyToCallAPISendGift = true;
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                isReadyToCallAPISendGift = true;
            }
        });
    }

    private HeartLayout heartLayout;

    private void playAnimFirst() {
        lTopRoom.setVisibility(View.INVISIBLE);
        lBottomRoom.setVisibility(View.INVISIBLE);
        lChatView.setVisibility(View.INVISIBLE);

        LAnimationUtil.play(lTopRoom, Techniques.SlideOutUp, new LAnimationUtil.Callback() {
            @Override
            public void onCancel() {
                //do nothing
            }

            @Override
            public void onEnd() {
                lTopRoom.setVisibility(View.VISIBLE);
                LAnimationUtil.play(lTopRoom, Techniques.SlideInDown);
                lBottomRoom.setVisibility(View.VISIBLE);
                LAnimationUtil.play(lBottomRoom, Techniques.SlideInUp);
                lChatView.setVisibility(View.VISIBLE);
                LAnimationUtil.play(lChatView, Techniques.SlideInLeft);
            }

            @Override
            public void onRepeat() {
                //do nothing
            }

            @Override
            public void onStart() {
                //do nothing
            }
        });
    }

    @Override
    public void onResume() {
        //LLog.d(TAG, "onResume");
        videoView.start();
        if (mUserProfile == null) {
            mUserProfile = LPref.getUser(getActivity());
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        //LLog.d(TAG, "onPause");
        videoView.pause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        //save mUserProfile
        if (mUserProfile != null) {
            LPref.setUser(getActivity(), mUserProfile);
        }

        heartLayout = null;
        super.onStop();
        videoView.onDestroy();
        RoomSocketHelper.getInstance().close();
        super.onDestroyView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_group_main:
                if (isFullScreenView) {
                    //show
                    LAnimationUtil.play(lTopRoom, Techniques.SlideInDown);
                    LAnimationUtil.play(lBottomRoom, Techniques.SlideInUp);
                    LAnimationUtil.play(lChatView, Techniques.SlideInLeft);
                } else {
                    if (isStateViewControllerLBottomView) {
                        //hide
                        LAnimationUtil.play(lTopRoom, Techniques.SlideOutUp);
                        LAnimationUtil.play(lBottomRoom, Techniques.SlideOutDown);
                        LAnimationUtil.play(lChatView, Techniques.SlideOutLeft);
                    } else {
                        //reset lBottomRoom to controller state
                        lBottomRoom.resetStateViewToController();
                        return;
                    }
                }
                isFullScreenView = !isFullScreenView;
                break;
        }
    }

    //follow and unfollow
    private void followIdol(String roomID) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.followIdol(roomID), new ApiSubscriber<FollowIdol>() {
            @Override
            public void onSuccess(FollowIdol followIdol) {
                LLog.d(TAG, "followIdol onSuccess " + LSApplication.getInstance().getGson().toJson(followIdol));
                if (followIdol != null && followIdol.getMessage() != null) {
                    AlertMessage.showSuccess(getActivity(), followIdol.getMessage());
                    LHomeUtil.reloadDataFollowOrSuggest();
                    if (roomID.equals(mRoomFindByID.getId())) {
                        mRoom.setIsFollow(!mRoom.isIsFollow());
                        mRoomFindByID.setIsFollow(!mRoomFindByID.isIsFollow());
                        lTopRoom.updateViewFollowUnfollow();
                    } else {
                        lTopRoom.updateUIFollowUnfollowVertical();
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void unfollowIdol(String roomID) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.unfollowIdol(roomID), new ApiSubscriber<FollowIdol>() {
            @Override
            public void onSuccess(FollowIdol followIdol) {
                LLog.d(TAG, "unfollowIdol onSuccess " + LSApplication.getInstance().getGson().toJson(followIdol));
                if (followIdol != null && followIdol.getMessage() != null) {
                    AlertMessage.showSuccess(getActivity(), followIdol.getMessage());
                    LHomeUtil.reloadDataFollowOrSuggest();

                    if (roomID.equals(mRoomFindByID.getId())) {
                        mRoomFindByID.setIsFollow(!mRoomFindByID.isIsFollow());
                        lTopRoom.updateViewFollowUnfollow();
                    } else {
                        lTopRoom.updateUIFollowUnfollowVertical();
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }
    //follow and unfollow

    //send heart
    private void sendHeart(int posX, int posY, int widthHeartButton, int heightHeartButton) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.sendHeart(mRoom.getId()), new ApiSubscriber<SendHeart>() {
            @Override
            public void onSuccess(SendHeart result) {
                if (result != null) {
                    //LLog.d(TAG, "sendHeart onSuccess getTotalHeart " + result.getTotalHeart());
                    //LLog.d(TAG, "setCallbackOnClick onClickHeartButton " + posX + ", " + posY);
                    if (heartLayout == null) {
                        heartLayout = new HeartLayout(getActivity());
                        heartLayout.setBackgroundColor(Color.TRANSPARENT);

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthHeartButton * 3, ViewGroup.LayoutParams.MATCH_PARENT);
                        params.leftMargin = posX - widthHeartButton - widthHeartButton / 2 + (int) (widthHeartButton / 2.75);
                        params.bottomMargin = heightHeartButton / 2;

                        viewGroupMain.addView(heartLayout, params);

                        LUIUtil.setDelay(100, new LUIUtil.DelayCallback() {
                            @Override
                            public void doAfter(int mls) {
                                heartLayout.addHeart(LStoreUtil.getRandomColor());
                                LLog.d(TAG, ">>>if");
                            }
                        });
                    } else {
                        heartLayout.addHeart(LStoreUtil.getRandomColor());
                        LLog.d(TAG, ">>>else");
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void getAudiences() {
        //UserProfile userProfile = LPref.getUser(getActivity());
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getAudiences(mRoom.getId(), page), new ApiSubscriber<Audiences>() {
            @Override
            public void onSuccess(Audiences result) {
                lstUser = result.getItems();
                lTopRoom.setupFirstList(lstUser);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RoomSocketHelper.getInstance().close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
