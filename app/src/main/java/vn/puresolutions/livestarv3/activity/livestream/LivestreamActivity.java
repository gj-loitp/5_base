package vn.puresolutions.livestarv3.activity.livestream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.artfulbits.libstream.Filters;
import com.artfulbits.libstream.Streamer;
import com.artfulbits.libstream.StreamerGL;
import com.artfulbits.libstream.StreamerGLBuilder;
import com.artfulbits.libstream.VideoEncoder;
import com.daimajia.androidanimations.library.Techniques;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.CommonResponse;
import vn.puresolutions.livestar.corev3.api.model.v3.endlive.EndLive;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.User;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestar.corev3.api.model.v3.startlive.StartLive;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.livestream.model.ChatMessage;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.utilities.v3.RoomSocketHelper;
import vn.puresolutions.livestarv3.view.LBottomLiveStream;
import vn.puresolutions.livestarv3.view.LBottomRoom;
import vn.puresolutions.livestarv3.view.LChatView;
import vn.puresolutions.livestarv3.view.LTopLivestream;

//TODO remove log tag

public class LivestreamActivity extends BaseActivity implements Streamer.Listener, View.OnClickListener {
    private StartLive sl;
    private LTopLivestream lTopLiveStream;
    private LBottomLiveStream lBottomLiveStream;
    private LChatView lChatView;
    private RelativeLayout viewGroupMain;
    private boolean isFullScreenView;
    private boolean isStateViewControllerLBottomView = true;
    private UserProfile mUserProfile;
    private final int REQUEST_CODE_DIALOG_ASK = 6969;
    private int mConnectionId = -1;
    private boolean mIsStreaming;
    protected StreamerGL mStreamerGL = null;
    private Handler mHandler = new Handler();
    private Streamer.CONNECTION_STATE mConnectionState;
    private boolean mCamera2 = false;
    private String mFrontCameraId;
    private String mBackCameraId;
    private String shareLink;
    private boolean isShareFB;
    private Streamer.Size video_size_front;
    private CallbackManager callbackManager;
    private Streamer.Size video_size_back;
    private boolean mFilterOn;
    private SurfaceView mPreview;
    private static final int MAX_RECONNECT_ATTEMPTS = 10;
    private static final Streamer.Size VIDEO_RES = new Streamer.Size(1280, 720);
    private boolean mIsFrontCamera = true;
    private int mRetryCount;
    private ArrayList<User> lstUser = new ArrayList<>();
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setCustomStatusBar(false);
        EventBus.getDefault().register(this);
        mUserProfile = LPref.getUser(activity);
        if (mUserProfile == null) {
            AlertMessage.showError(activity, R.string.err_unknow);
            onBackPressed();
        }
        findViews();
        initTopView();
        initBottomView();
        RoomSocketHelper.getInstance().setCallback(new RoomSocketHelper.Callback() {
            @Override
            public void onNewComment(ChatMessage message) {
                if (lChatView != null) {
                    LLog.d(TAG, "LChatView !null");
                    lChatView.addMessage(message);
                } else {
                    LLog.d(TAG, "LChatView null");
                }
            }

            @Override
            public void onNewConnectUser(ChatMessage message) {
                lTopLiveStream.addItemFirst(message.getUser());
                lTopLiveStream.setTvAudiences();
            }

            @Override
            public void onDisconnect(ChatMessage message) {
                LLog.d(TAG, "onDisconnect->remove");
                lTopLiveStream.removeItem(message.getUser());
                lTopLiveStream.setTvAudiences();
            }

            @Override
            public void onSendHeart(ChatMessage message) {

            }

            @Override
            public void onSendGift(ChatMessage message) {

            }
        });
    }

    SurfaceHolder.Callback mPreviewCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            LLog.d(TAG, "surfaceCreated");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    initEncoders(holder);
                }
            }).start();
            // if (mIsStreaming) (findViewById(R.id.btn_start_stop)).callOnClick();
            if (mFilterOn) {
                mFilterOn = false;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //LLog.d(TAG, "MainActivity: surfaceChanged() " + width + "x" + height);
            LLog.d(TAG, "surfaceChanged");
            if (null != mStreamerGL) {
                //LLog.d(TAG, "MainActivity: surfaceChanged() set " + width + "x" + height);
                //mStreamerGL.setSurfaceSize(new Streamer.Size(width, height));
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            //LLog.d(TAG, "surfaceDestroyed");
            if (null != mStreamerGL)
                //LLog.d(TAG, "surfaceDestroyed -> null != mStreamerGL");
                mStreamerGL.stopVideoCapture();
        }
    };

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
        return R.layout.activity_livestream;
    }

    private void findViews() {
        viewGroupMain = (RelativeLayout) findViewById(R.id.view_group_main);
        viewGroupMain.setOnClickListener(this);
        lTopLiveStream = (LTopLivestream) findViewById(R.id.l_top_live_stream);
        lBottomLiveStream = (LBottomLiveStream) findViewById(R.id.l_bottom_live_stream);
        lChatView = (LChatView) findViewById(R.id.l_chat_view);
        playAnimFirst();
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);

        int h = lBottomLiveStream.getHeightViewGroupBottomMain();
        //LLog.d(TAG, ">>>h " + h);
        int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                dimensionInDp
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.setMargins(0, 0, 0, h);
        lChatView.setLayoutParams(params);

        lBottomLiveStream.setActionCallback(new LBottomLiveStream.ActionCallback() {
            @Override
            public void onFlipCamera() {
                mStreamerGL.flip();
                mIsFrontCamera = !mIsFrontCamera;
            }

            @Override
            public void onFilterMode() {
                if (null != mStreamerGL) {
                    mFilterOn = !mFilterOn;

                    mStreamerGL.setFilter((mFilterOn) ? Filters.FILTER_BILATERAL : Filters.FILTER_NONE);

                    //int _img = (!mFilterOn) ? R.drawable.ic_retouch : R.drawable.ic_retouch_2;

                    //((ImageView) findViewById(R.id.btn_apply_filter)).setImageResource(_img);
                }
            }

            @Override
            public void onShareFb() {
                isShareFB = true;
                shareFb();
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
                    lBottomLiveStream.updateUIButtonFacebook();
                    isShareFB = false;
                    AccessToken accessToken = AccessToken.getCurrentAccessToken();
                    LLog.d(TAG, "FB Share " + accessToken.getToken());
                    LSService service = RestClient.createService(LSService.class);
                    subscribe(service.shareFB(accessToken.getToken(), LPref.getUser(activity).getRoom().getId()), new ApiSubscriber<CommonResponse>() {
                        @Override
                        public void onSuccess(CommonResponse result) {
                            /*if (result.getError() == null) {
                                LLog.d(TAG, "Share success");
                                AlertMessage.showSuccess(activity, "Bạn đã share thành công, nhận được 10 EXP");
                            }*/
                            AlertMessage.showSuccess(activity, result.getMessage());
                        }

                        @Override
                        public void onFail(Throwable e) {
                            handleException(e);
                        }
                    });

                }

                @Override
                public void onCancel() {
                    isShareFB = false;
                    LLog.d(TAG, "cancel");
                }

                @Override
                public void onError(FacebookException error) {
                    isShareFB = false;
                    LLog.d(TAG, "error: " + error.getMessage());
                }
            });
            shareDialog.show(linkContent);
        }
    }

    private void initTopView() {
        lTopLiveStream.setData(mUserProfile);
        lTopLiveStream.setCallback(new LTopLivestream.Callback() {
            @Override
            public void onClickBack() {
                showDialogConfirm();
            }
        });
    }

    private void initBottomView() {
        lBottomLiveStream.setData(mUserProfile);
        lBottomLiveStream.setStateCallback(new LBottomLiveStream.StateCallback() {
            @Override
            public void onStateViewChange(String stateView) {
                //LLog.d(TAG, "onStateViewChange " + stateView);
                isStateViewControllerLBottomView = stateView.equals(LBottomRoom.STATE_VIEW_CONTROLLER);
                //LLog.d(TAG, ">>>isStateViewControllerLBottomView " + isStateViewControllerLBottomView);
            }
        });
        lBottomLiveStream.setModeCallback(new LBottomLiveStream.ModeCallback() {
            @Override
            public void onModeChange(String mode) {
                setModeRoom(mode);
            }
        });
        lBottomLiveStream.setOnChatSend(new LBottomLiveStream.OnChatSend() {
            @Override
            public void OnSend(String text) {
                //LLog.d(TAG, "send chat: " + text);
                RoomSocketHelper.getInstance().sendChat(activity, text);
            }
        });
    }

    private void playAnimFirst() {
        lTopLiveStream.setVisibility(View.INVISIBLE);
        lBottomLiveStream.setVisibility(View.INVISIBLE);
        lChatView.setVisibility(View.INVISIBLE);

        LAnimationUtil.play(lTopLiveStream, Techniques.SlideOutUp, new LAnimationUtil.Callback() {
            @Override
            public void onCancel() {
                //do nothing
            }

            @Override
            public void onEnd() {
                lTopLiveStream.setVisibility(View.VISIBLE);
                LAnimationUtil.play(lTopLiveStream, Techniques.SlideInDown);
                lBottomLiveStream.setVisibility(View.VISIBLE);
                LAnimationUtil.play(lBottomLiveStream, Techniques.SlideInUp);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_group_main:
                if (isFullScreenView) {
                    //show
                    //LLog.d(TAG, "show");
                    LAnimationUtil.play(lTopLiveStream, Techniques.SlideInDown);
                    LAnimationUtil.play(lBottomLiveStream, Techniques.SlideInUp);
                    LAnimationUtil.play(lChatView, Techniques.SlideInLeft);
                } else {
                    if (isStateViewControllerLBottomView) {
                        //hide
                        //LLog.d(TAG, "hide");
                        LAnimationUtil.play(lTopLiveStream, Techniques.SlideOutUp);
                        LAnimationUtil.play(lBottomLiveStream, Techniques.SlideOutDown);
                        LAnimationUtil.play(lChatView, Techniques.SlideOutLeft);
                    } else {
                        //reset lBottomRoom to controller state
                        //LLog.d(TAG, "reset lBottomRoom to controller state");
                        lBottomLiveStream.resetStateViewToController();
                        return;
                    }
                }
                isFullScreenView = !isFullScreenView;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        showDialogConfirm();
    }

    private void showDialogConfirm() {
        LDialogUtils.showDlg2Option(activity, 0, getString(R.string.reminder), getString(R.string.do_you_want_to_exit_livestream), getString(R.string.exit_livestream_yes), getString(R.string.exit_livestream_no), new LDialogUtils.Callback2() {
            @Override
            public void onClickButton0() {
                isShareFB = true;
                boolean isIdol = mUserProfile.getIsIdol() == Constants.USER_IS_IDOL;
                int mode;
                if (isIdol) {
                    mode = SaveVideoActivity.MODE_ASK_SAVE_VIDEO;
                } else {
                    mode = SaveVideoActivity.MODE_SHOW_DETAIL_VIDEO;
                }
                Intent intent = new Intent(activity, SaveVideoActivity.class);
                intent.putExtra(Constants.LIVESTREAM_SAVE_VIDEO_OR_SHOW_DETAIL, mode);
                startActivityForResult(intent, REQUEST_CODE_DIALOG_ASK);
                LUIUtil.transActivityFadeIn(activity);
            }

            @Override
            public void onClickButton1() {
                //do nothing
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //LLog.d(TAG, "onActivityResult");
        if (requestCode == REQUEST_CODE_DIALOG_ASK && resultCode == RESULT_OK) {
            //LLog.d(TAG, ">>>");
            boolean backData = data.getBooleanExtra(Constants.KEY_LIVESTREAM_ASK_SAVE_VIDEO, false);
            //LLog.d(TAG, "backData: " + backData);
            if (backData) {
                LUIUtil.setDelay(300, new LUIUtil.DelayCallback() {
                    @Override
                    public void doAfter(int mls) {
                        //LLog.d(TAG, "onActivityResult ");
                        finish();
                        LUIUtil.transActivityFadeIn(activity);
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setModeRoom(String modeRoom) {
        if (!modeRoom.equals(Constants.MODE_PUBLIC) && !modeRoom.equals(Constants.MODE_RESTRICT) && !modeRoom.equals(Constants.MODE_PRIVATE)) {
            return;
        }
        //LLog.d(TAG, "onModeChange mode " + modeRoom);
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.setModeRoom(modeRoom), new ApiSubscriber<Room>() {
            @Override
            public void onSuccess(Room room) {
                //LLog.d(TAG, "onSuccess setModeRoom " + LSApplication.getInstance().getGson().toJson(room));
                String newModeRoom = room.getMode();
                lBottomLiveStream.updateUIMode(newModeRoom);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void endLiveStream() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.endLive(), new ApiSubscriber<EndLive>() {
            @Override
            public void onSuccess(EndLive endLive) {
                //LLog.d(TAG, "endLiveStream onSuccess " + LSApplication.getInstance().getGson().toJson(endLive));
                releaseConnection();
                RoomSocketHelper.getInstance().close();

                //start eventbus
                SaveVideoActivity.EndLiveEvent endLiveEvent = new SaveVideoActivity.EndLiveEvent();
                endLiveEvent.setEndLive(endLive);
                EventBus.getDefault().post(endLiveEvent);
                //end eventbus
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void startLivestream() {
        LSService service = RestClient.createService(LSService.class);
        String currentMode = mUserProfile.getRoom().getMode();
        subscribe(service.startLive(currentMode), new ApiSubscriber<StartLive>() {
            @Override
            public void onSuccess(StartLive startLive) {
                LLog.d(TAG, "startLivestream onSuccess " + LSApplication.getInstance().getGson().toJson(startLive));
                //TODO startLivestream???
                sl = startLive;
                createConnection(startLive.getBroadcastUrl());
                connectAndStartRoom();
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void createConnection(String url) {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            //LLog.d(TAG, "createConnection -> URISyntaxException " + e.toString());
            //Toast.makeText(this, R.string.an_error_has_occurred, Toast.LENGTH_SHORT);
            return;
        } catch (Exception e) {
            //LLog.d(TAG, "createConnection -> Exception " + e.toString());
        }

        //LLog.d(TAG, "Remote stream: " + url);

        Streamer.MODE mode = Streamer.MODE.AUDIO_VIDEO;
        LLog.d(TAG, "Remote stream: " + uri.toString());
        mConnectionId = mStreamerGL.createConnection(uri.toString(), mode, this);
        //LLog.d(TAG, "createConnection -> mConnectionId " + mConnectionId);
        mIsStreaming = !(mConnectionId == -1);

        if (mConnectionId == -1) {
            //Toast.makeText(MainActivity.this, "Network failure, could not connect to server.", Toast.LENGTH_LONG).show();
            //LLog.d(TAG, "createConnection -> Network failure, could not connect to server ");
            mIsStreaming = false;
        }

        //updateButtons();
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void OnConnectionStateChanged(int id, Streamer.CONNECTION_STATE state, Streamer.STATUS status) {
        if (null == mStreamerGL) {
            return;
        }

        if (id == mConnectionId) {
            mConnectionState = state;
        }

        switch (state) {
            case INITIALIZED:
                break;
            case CONNECTED:
//                connTime_ = prevTime_ = System.currentTimeMillis();
//                prevBytes_ = mStreamer.getBytesSent(mConnectionId);
                break;
            case SETUP:
                break;
            case RECORD:
                break;
            case DISCONNECTED:
                //LLog.d(TAG, "OnConnectionStateChanged -> DISCONNECTED");
                //LLog.d(TAG, "OnConnectionStateChanged -> mConnectionId " + mConnectionId);
                //LLog.d(TAG, "OnConnectionStateChanged -> id " + id);
                if (id != mConnectionId) {
                    //LLog.e(TAG, "unregistered connection");
                    break;
                }

                int delay = 3000;
                String _msg = "";
                if (mIsStreaming) {
                    if (status == Streamer.STATUS.CONN_FAIL) {
                        _msg = "Network failure, could not connect to server.";
                    } else if (status == Streamer.STATUS.AUTH_FAIL) {
                        _msg = "Authentication failure, please check stream credentials.";
                    } else {
                        _msg = "Unknown connection failure.";
                    }
                    //LLog.w(TAG, _msg);
                    releaseConnection();
                    mRetryCount++;
                }

                if (mRetryCount > MAX_RECONNECT_ATTEMPTS) {
                    mIsStreaming = false;
                    mRetryCount = 0;
                   /* LivestreamActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //showResultDialog(R.string.msg_text_disconnected);
                            Log
                        }
                    });*/
                }

                if (mIsStreaming) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mIsStreaming) {
                                createConnection(sl.getBroadcastUrl());
                            }
                        }
                    }, delay);
                }
                break;
        }
    }

    protected void releaseConnection() {
        if (mConnectionId == -1) {
            return;
        }

        mStreamerGL.releaseConnection(mConnectionId);
        mConnectionId = -1;
        mIsStreaming = false;
    }

    @Override
    public void OnVideoCaptureStateChanged(Streamer.CAPTURE_STATE capture_state) {
        switch (capture_state) {
            case STARTED:
                break;
        }

    }

    @Override
    public void OnAudioCaptureStateChanged(Streamer.CAPTURE_STATE capture_state) {
    }

    private void initEncoders(SurfaceHolder holder) {
        //Create Streamer
        Streamer.FpsRange _fpsRange = new Streamer.FpsRange(15000, 24000);

        StreamerGLBuilder builder = new StreamerGLBuilder(this);
        builder.setListener(this);
        //builder.setUserAgent("LiveStar Broadcaster App" + "/" + BuildConfig.VERSION_NAME + "-" + BuildConfig.VERSION_CODE);
        builder.setUserAgent("Yup" + "/" + "1" + "-" + 1);
        builder.setKeyFrameInterval(1);
        //Use Camera2 API
        builder.setCamera2(mCamera2);

        //identify cameras
        List<Streamer.CameraInfo> cameraList = builder.getCameraList(this, mCamera2);
        for (Streamer.CameraInfo _info : cameraList) {
            if (!_info.lensFacingBack) {
                mFrontCameraId = _info.cameraId;
                video_size_front = findClosestRes(VIDEO_RES, _info.recordSizes);
                //LLog.i(TAG, "initEncoders->video_size_front: " + video_size_front.width + " " + video_size_front.height);
                _fpsRange = findMatchingFPS(_fpsRange, _info.fpsRanges);
            } else {
                mBackCameraId = _info.cameraId;
                video_size_back = findClosestRes(VIDEO_RES, _info.recordSizes);
            }
        }

        String _id = LPref.getStoredCameraId(activity);
        mIsFrontCamera = (_id.isEmpty() || _id.compareToIgnoreCase(mFrontCameraId) == 0);
        builder.setCameraId(mIsFrontCamera ? mFrontCameraId : mBackCameraId);

        builder.setVideoSize(video_size_back);
        builder.setFrameRate(_fpsRange);
        int bitRate = 1000000;//VideoEncoder.calcBitRate(video_size_front, 30, MediaCodecInfo.CodecProfileLevel
        //.AVCProfileMain);
        //LLog.i(TAG, "Output video stream bitrate: " + bitRate);
        builder.setBitRate(bitRate);
        //Audio
        builder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        builder.setSampleRate(44100);
        builder.setChannelCount(1);
        //GLES
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                builder.setSurface(holder.getSurface());

                builder.setSurfaceSize(calcPreviewSize(video_size_front));
            }
        });

        // Rotate preview
        int _r = this.getWindowManager().getDefaultDisplay().getRotation();
        // This will be later set to device orientation when user press "Broadcast" button
        //        builder.setVideoRotation(0);
        //        builder.setDisplayRotation(0);

        //add front camera
        builder.addCamera(mFrontCameraId, video_size_front);
        //add back camera
        builder.addCamera(mBackCameraId, video_size_back);

        //Starting cupturing from Audio and Video source
        //LLog.d(TAG, "w: " + video_size_front.width + " h: " + video_size_front.height);
        VideoEncoder videoEncoder = VideoEncoder.createVideoEncoder(video_size_front);
        mStreamerGL = builder.build();
        mStreamerGL.startVideoCapture();
        mStreamerGL.startVideoCapture(this, mFrontCameraId, holder, videoEncoder, this, 1);
        mStreamerGL.setVideoOrientation(1);
        mStreamerGL.startAudioCapture();

        //mStreamerGL.setDisplayRotation((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 1 : 0);
        mStreamerGL.setDisplayRotation((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 1 : 0);
        startLivestream();
    }

    private Streamer.Size findClosestRes(Streamer.Size requested, Streamer.Size[] recordSizes) {
        Streamer.Size _res = null;
        for (Streamer.Size _s : recordSizes) {
            if ((requested.width == _s.width) && (requested.height == _s.height)) {
                _res = _s;
                //LLog.i(TAG, "findClosestRes w: " + _s.width + " h: " + _s.height);
                break;
            }
        }
        return _res;
    }

    private Streamer.Size calcPreviewSize(Streamer.Size video_size) {
        int r_h = mPreview.getHeight() + getBottomBarHeight() + getStatusBarHeight();
        ViewGroup.LayoutParams params = mPreview.getLayoutParams();
        params.height = r_h;
        //LLog.i(TAG, "calcPreviewSize navi: " + getBottomBarHeight());
        //LLog.i(TAG, "calcPreviewSize status: " + getStatusBarHeight());
        //LLog.i(TAG, "calcPreviewSize height: " + r_h);
        //LLog.i(TAG, "calcPreviewSize ratio: " + video_size.getRatio());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            params.width = (int) (r_h / video_size.getRatio());
            //LLog.i(TAG, "calcPreviewSize -> PORT: " + params.width);
        } else {
            params.width = (int) (r_h * video_size.getRatio());
            //LLog.i(TAG, "calcPreviewSize -> LANS: " + params.width);
        }
        mPreview.setLayoutParams(params);

        //LLog.i(TAG, "calcPreviewSize w: " + params.width + " h: " + params.height);
        return new Streamer.Size(params.width, params.height);
        //return new Streamer.Size(params.height, params.width);
    }

    private Streamer.FpsRange findMatchingFPS(Streamer.FpsRange fpsRange, Streamer.FpsRange[] fpsRanges) {
        //LLog.i(TAG, String.format("Requested FPS = %.2f, %.2f", fpsRange.fps_min, fpsRange.fps_max));
        if (fpsRanges.length == 1)
            return fpsRanges[0];

        int _minmaxId = 0;

        for (int i = 1; i < fpsRanges.length; i++) {
            float _prev = Math.max(Math.abs(fpsRange.fps_min - fpsRanges[_minmaxId].fps_min),
                    Math.abs(fpsRange.fps_max - fpsRanges[_minmaxId].fps_max));
            float _cur = Math.max(Math.abs(fpsRange.fps_min - fpsRanges[i].fps_min),
                    Math.abs(fpsRange.fps_max - fpsRanges[i].fps_max));
            if (_cur < _prev)
                _minmaxId = i;
        }
        //LLog.i(TAG, String.format("Best matching FPS = %.2f, %.2f", fpsRanges[_minmaxId].fps_min, fpsRanges[_minmaxId].fps_max));
        return fpsRanges[_minmaxId];
    }

    private void connectAndStartRoom() {
        avLoadingIndicatorView.setVisibility(View.GONE);
        UserProfile userProfile = LPref.getUser(activity);
        LLog.d(TAG, "Token:" + LPref.getToken(activity));
        LLog.d(TAG, "id:" + userProfile.getRoom().getId());
        RoomSocketHelper.getInstance().open(userProfile.getRoom().getId(), LPref.getToken(activity), new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                // lTopLiveStream.setTvAudiences();
                LLog.d(TAG, "connected");
                //startLivestream();
                //createConnection();
                //getAudiences();
            }
        });
    }

    @Override
    protected void onDestroy() {
        LLog.d(TAG, "onDestroy()");
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
        RoomSocketHelper.getInstance().close();
        if (mStreamerGL != null) {
            mStreamerGL.release();
            mStreamerGL = null;
            releaseConnection();
        }
        mPreview = null;
    }

    @Override
    protected void onPause() {
        LLog.d(TAG, "onPause");
        super.onPause();
        LPref.storeFilterState(activity, mFilterOn);
        LPref.storeCameraId(activity, mIsFrontCamera ? mFrontCameraId : mBackCameraId);
        if (!isShareFB) {
            if (mIsStreaming) {
                LLog.d(TAG, "onPause -> mIsStreaming -> releaseConnection()");
                releaseConnection();
            }
            mIsStreaming = false;
            mPreview.getHolder().removeCallback(null);
            if (mStreamerGL != null) {
                LLog.d(TAG, "onPause -> mStreamerGL != null -> stopVideo");
                mStreamerGL.stopVideoCapture();
                mStreamerGL.stopAudioCapture();
                mStreamerGL.release();
                mStreamerGL = null;
            }
        }
       /* if (mIsStreaming) {
            releaseConnection();
        }
        mIsStreaming = false;
        mPreview.getHolder().removeCallback(null);

        if (mStreamerGL != null) {
            mStreamerGL.stopVideoCapture();
            mStreamerGL.stopAudioCapture();
            mStreamerGL.release();
            mStreamerGL = null;
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFilterOn = LPref.getStoredFilterState(activity);
        LLog.d(TAG, "onResume");
        mPreview = (SurfaceView) findViewById(R.id.surface);
        SurfaceHolder surfaceHolder = mPreview.getHolder();
        surfaceHolder.addCallback(mPreviewCallback);
    }

   /* @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d(TAG, "portrait");
        }

        if (null != mStreamerGL) {
            mStreamerGL.setDisplayRotation((newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) ? 1 : 0);
            mStreamerGL.setVideoOrientation((newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) ? 0 : 0);
        }
    }*/

    //event bus listen
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SaveVideoActivity.SaveVideoMessage saveVideoMessage) {
        if (saveVideoMessage != null) {
            LLog.d(TAG, "onMessageEvent click: " + saveVideoMessage.isSavedClick());
            endLiveStream();
            isShareFB = false;
            /*if (mStreamerGL!=null){
                mStreamerGL.release();
                mStreamerGL = null;
            }*/
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_POWER) {
            LLog.d(TAG, "Power press");
            // do what you want with the power button
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        //LLog.d(TAG, "result bar height: " + result);
        return result;
    }

    public int getBottomBarHeight() {
        boolean hasMenuKey = ViewConfiguration.get(activity).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // Do whatever you need to do, this device has a navigation bar
            int result = 0;
            int resourceId = getResources().getIdentifier("design_bottom_navigation_height", "dimen", getPackageName());
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            //LLog.d(TAG, "result botbar height: " + result);
            return result;
        }
        return 0;
    }
    /*private void getAudiences(){
        UserProfile userProfile = LPref.getUser(activity);
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getAudiences(userProfile.getRoom().getId(),page), new ApiSubscriber<Audiences>() {
            @Override
            public void onSuccess(Audiences result) {
                lstUser = result.getItems();
                lTopLiveStream.setupFirstList(lstUser);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }*/
}
