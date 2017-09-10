package vn.puresolutions.livestar.room;

import android.content.Context;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.Action;
import vn.puresolutions.livestar.core.api.model.Broadcaster;
import vn.puresolutions.livestar.core.api.model.GiftMessage;
import vn.puresolutions.livestar.core.api.model.HeartMessage;
import vn.puresolutions.livestar.core.api.model.Lounge;
import vn.puresolutions.livestar.core.api.model.Media;
import vn.puresolutions.livestar.core.api.model.Message;
import vn.puresolutions.livestar.core.api.model.RoomDetail;
import vn.puresolutions.livestar.core.api.model.Schedule;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.model.UserSocket;
import vn.puresolutions.livestar.core.api.model.Users;
import vn.puresolutions.livestar.core.api.model.Vip;
import vn.puresolutions.livestar.core.api.model.VipComingMessage;
import vn.puresolutions.livestar.core.api.model.param.FollowParam;
import vn.puresolutions.livestar.core.api.model.param.HeartParam;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.BroadcasterService;
import vn.puresolutions.livestar.core.api.service.LiveService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.helper.NotificationCenter;
import vn.puresolutions.livestar.room.status.RoomSocketStatus;
import vn.puresolutions.livestar.room.status.RoomStatus;
import vn.puresolutions.livestarv3.utilities.old.MediaUtils;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 12/3/2015
 */

public class RoomCenter {
    private static final int ADD_HEART_TIME = 60000;
    private static volatile RoomCenter Instance = null;

    public int roomId;
    public long idolId;
    public List<Object> ranking = new ArrayList<>();
    public Users users = new Users();
    public List<Schedule> schedules;

    public RoomDetail roomDetail;
    public Broadcaster idolProfile;
    public String idolName;

    // Helpers
    private RoomSocketHelper roomSocketHelper;
    private RoomApiHelper roomApiHelper;
    private CountDownTimer countDownTimerHeart;

    // Status
    public RoomStatus roomStatus = RoomStatus.NONE;
    private RoomSocketStatus roomSocketStatus = RoomSocketStatus.NONE;

    private IRoomView roomView;
    private CompositeSubscription compositeSubscription;

    private NotificationCenter.NotificationCenterListener notificationCenterListener =
            new NotificationCenter.NotificationCenterListener() {
                @Override
                public void didReceivedNotification(int id, Object... args) {
                    if (id == NotificationCenter.UserLoggedIn) {
                        // reset connect
                        roomSocketStatus = RoomSocketStatus.CONNECTING;
                        roomSocketHelper.open(UserInfo.getUserLoggedIn());
                    }
                }
            };

    public static RoomCenter getInstance() {
        RoomCenter localInstance = Instance;
        if (localInstance == null) {
            synchronized (RoomCenter.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new RoomCenter();
                }
            }
        }
        return localInstance;
    }

    private RoomCenter() {
        // singleton instance
    }

    public void onCreate(Context context, IRoomView roomView, RoomDetail detail) {
        this.roomDetail = detail;
        this.idolProfile = roomDetail.getBroadcaster();
        this.idolName = idolProfile.getName();
        this.roomId = roomDetail.getId();
        this.idolId = idolProfile.getId();
        this.schedules = roomDetail.getSchedules();

        roomSocketHelper = new RoomSocketHelper(context, this);
        roomApiHelper = new RoomApiHelper(context, this);
        compositeSubscription = new CompositeSubscription();
        NotificationCenter.getInstance().addObserver(notificationCenterListener, NotificationCenter.UserLoggedIn);

        //play stream
        roomView.playStream(roomDetail.getStreamLink());

        // connect socket
        connectSocket();

        //loadIdolProfile();
    }

    public void onDestroy() {
        roomApiHelper.cancelAllTask();
        roomSocketHelper.close();
        Instance = null;
        if (countDownTimerHeart != null) {
            countDownTimerHeart.cancel();
            countDownTimerHeart = null;
        }
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
        NotificationCenter.getInstance().removeObserver(notificationCenterListener, NotificationCenter.UserLoggedIn);
    }

    private void connectSocket() {
        User user = UserInfo.getUserLoggedIn();
        if (user == null) {
            user = roomDetail.getSocketUser();
        }
        roomSocketStatus = RoomSocketStatus.CONNECTING;
        roomSocketHelper.open(user);
    }


    public void loadIdolProfile() {
        roomApiHelper.loadIdolProfile(idolId);
    }

    public boolean followIdol() {
        boolean isFollow = !roomDetail.getBroadcaster().isFollow();
        roomDetail.getBroadcaster().setIsFollow(isFollow);
        BroadcasterService service = RestClient.createService(BroadcasterService.class);
        Observable<Void> observable = service.follow(roomDetail.getBroadcaster().getId(),
                new FollowParam(isFollow ? "Follow" : "Unfollow"));
        subscribe(observable, new ApiSubscriber() {
            @Override
            public void onSuccess(Object result) {
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.FollowIdolSuccess, isFollow);
            }

            @Override
            public void onFail(Throwable e) {
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.FollowIdolFail, !isFollow);
                roomDetail.getBroadcaster().setIsFollow(!isFollow);
            }
        });

        return isFollow;
    }

    private void startCountDownAddHeart() {
        if (countDownTimerHeart != null) {
            countDownTimerHeart.cancel();
            countDownTimerHeart = null;
        }
        countDownTimerHeart = new CountDownTimer(2 * 60 * ADD_HEART_TIME, ADD_HEART_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {
                User user = UserInfo.getUserLoggedIn();
                if (user == null) {
                    countDownTimerHeart.cancel();
                    return;
                }
                int userHeart = user.getHeart();
                if (userHeart < user.getMaxHeart()) {
                    addHeart();
                }
            }

            @Override
            public void onFinish() {
                // do nothing
            }
        };
        countDownTimerHeart.start();
    }

    private void addHeart() {
        LiveService service = RestClient.createService(LiveService.class);
        subscribe(service.addHeart(new HeartParam(roomId)), new ApiSubscriber<Void>() {
            @Override
            public void onSuccess(Void result) {
                User user = UserInfo.getUserLoggedIn();
                int userHeart = user.getHeart();
                if (userHeart < user.getMaxHeart()) {
                    userHeart += 1;
                    user.setHeart(userHeart);
                    UserInfo.saveUserLogged();
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.UserReceiveHeart, userHeart);
                }
            }

            @Override
            public void onFail(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    public void sendHeart() {
        LiveService service = RestClient.createService(LiveService.class);
        subscribe(service.sendHeart(new HeartParam(roomId)), new ApiSubscriber() {
            @Override
            public void onSuccess(Object result) {
                // do nothing
            }

            @Override
            public void onFail(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings("unchecked")
    protected void subscribe(Observable observable, Subscriber subscriber) {
        Subscription subscription = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        compositeSubscription.add(subscription);
    }

    ////////////////////////////////// Socket Callbacks  /////////////////////////////////

    public void onSocketConnected(boolean isSuccess) {
        roomSocketStatus = isSuccess ? RoomSocketStatus.CONNECTED : RoomSocketStatus.ERROR;
        if (isSuccess) {
            if (UserInfo.isLoggedIn()) {
                startCountDownAddHeart();

                User userLogged = UserInfo.getUserLoggedIn();
                if (userLogged != null && userLogged.getVip() != null && userLogged.getVip().getVip() > 0) {
                    VipComingMessage message = new VipComingMessage();
                    message.setSender(userLogged);
                    Vip vip = new Vip();
                    vip.setVip(userLogged.getVip().getVip());
                    message.setVip(vip);
                    onReceiveNewMessage(message);

                    MediaUtils.playVipComingSound(LSApplication.getInstance());
                }
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.RoomSocketConnected, null);
        } else {
            //TODO
        }
    }

    public void onReceiveNewMessage(Message message) {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.ChatReceivedNewMessage, message);
    }

    public void onActionChanged(Action action) {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.ReceivedAction, action);
    }

    public void onLoungeChanged(Lounge lounge) {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.BuyLounge, lounge);
    }

    public void onReceiveListUser(Users users) {
        this.users.clear();
        for (UserSocket user : users) {
            if (user != null) {
                this.users.add(user);
                Collections.sort(this.users, (lhs, rhs) -> {
                    if (lhs.getVip() > rhs.getVip()) return -1;
                    return 0;
                });
            }
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.UserList);
    }

    public void onReceiveNewUser(UserSocket user) {
        if (user != null) {
            int index = -1;
            if (user.getVip() > 0) {
                index = 0;
                while (index < users.size()) {
                    if (this.users.get(index).getVip() < user.getVip()) {
                        this.users.add(index, user);
                        break;
                    }
                    index++;
                }
            } else {
                this.users.add(user);
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.UserNew);
        }
    }

    public void onReceiveUserLeave(Long userId) {
        for (UserSocket user : users) {
            if (user.getId() == userId) {
                users.remove(user);
                break;
            }
        }
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.UserLeave);
    }

    public void onReceivedGift(final GiftMessage data) {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.ChatReceivedNewMessage, data);
    }

    public void onReceivedHeart(final HeartMessage data) {
        //messages.add(data);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.HeartReceivedNewMessage);
        //NotificationCenter.getInstance().postNotificationName(NotificationCenter.ChatReceivedNewMessage, messages.size() - 1);
    }

    ////////////////////////////////// Api Callbacks  /////////////////////////////////

//    public void onLoadRoomDetailCompleted(final RoomDetail room) {
//        this.roomDetail = room;
//        roomStatus = RoomStatus.OPEN;
//
//        // load Idol's profile
//        roomApiHelper.loadIdolProfile(room.getBroadcaster().getId());
//
//        // save room schedule
//        schedules = room.getSchedules();
//
////        room.setOnAir(true);
////        room.setStreamLink("http://123.30.104.174:10908/vivo/_definst_/mp4:video/" +
////                        "the_time_we_were_not_in_love/the_time_we_were_not_in_love_001_2_high.mp4/playlist.m3u8");
//        if (room.isOnAir()) {
//            //roomScreen.openVideo(room.getStreamLink());
//            if (UserInfo.isLoggedIn()) {
//                connectSocket(UserInfo.getUserLoggedIn());
//            } else {
//                connectSocket(room.getSocketUser());
//            }
//        } else if (room.getVideos() != null && !room.getVideos().isEmpty()){
//            //roomScreen.showOfflineView();
//
//            Media media = room.getVideos().get(0);
//            openVideo(media);
//        }
//
//        NotificationCenter.getInstance().postNotificationName(NotificationCenter.RoomLoaded);
//    }

    public void openVideo(Media media) {
        if ("vod".equalsIgnoreCase(media.getType())) {
            //roomScreen.openVideo(media.getLink());
        } else {
            //roomScreen.openYoutube(media.getLink());
        }
    }

    public void onLoadRoomDetailFailed(Exception exception) {
        roomStatus = RoomStatus.ERROR;
        //roomScreen.showError(R.string.ls_connect_socket_timeout);
    }

    public void onLoadIdolProfileCompleted(Broadcaster broadcaster) {
        this.idolProfile = broadcaster;
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.LoadIdolCompleted);

        if (roomDetail.isOnAir()) return;

        // get random a video to play
//        List<Media> videos = broadcaster.getVideos();
//        if (videos != null && videos.size() > 0) {
//            Media video = videos.get(new Random().nextInt(videos.size()));
//            roomScreen.openYoutube(video.getLink());
//
//            roomScreen.showError(R.string.ls_idolOfflineAndNotifyViewYoutube);
//        } else {
//            // don't have any videos
//            // show banner
//            if (!TextUtils.isEmpty(roomDetail.getThumbApp())) {
//                roomScreen.showBanner(roomDetail.getThumbApp());
//            } else {
//                roomScreen.showBanner(roomDetail.getThumb());
//            }
//            roomScreen.showError(R.string.ls_idolOffline);
//        }
//        if (roomDetail.getVideos() == null || roomDetail.getVideos().isEmpty()) {
//            roomScreen.showBanner(roomDetail.getThumbApp());
//            roomScreen.showError(R.string.ls_idolOffline);
//        }
    }

    public void onLoadIdolProfileFailed(Exception exception) {
        //TODO
    }

    public void onChangeFollowStatusCompleted() {
        boolean isFollowed = roomDetail.getBroadcaster().isFollow();
        roomDetail.getBroadcaster().setIsFollow(!isFollowed);
    }

    public void onChangeFollowStatusFailed() {

    }

    public void onDuplicated() {
        //if (context != null && context instanceof MainActivity) {
        //TODO
        //((MainActivity) context).alertAndCloseRoom(R.string.ls_token_invalid);
        //    }
    }
}
