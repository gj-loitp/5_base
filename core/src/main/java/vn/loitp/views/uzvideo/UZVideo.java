package vn.loitp.views.uzvideo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.uiza.FUZService;
import vn.loitp.core.utilities.LConnectivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.data.EventBusData;
import vn.loitp.data.UZData;
import vn.loitp.restapi.uiza.UZRestClient;
import vn.loitp.restapi.uiza.UZRestClientGetLinkPlay;
import vn.loitp.restapi.uiza.UZService;
import vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay.ResultGetLinkPlay;
import vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay.Url;
import vn.loitp.restapi.uiza.model.v3.linkplay.gettokenstreaming.ResultGetTokenStreaming;
import vn.loitp.restapi.uiza.model.v3.linkplay.gettokenstreaming.SendGetTokenStreaming;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.utils.util.ServiceUtils;
import vn.loitp.views.exo.PlayerManager;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class UZVideo extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private PlayerView playerView;
    private PlayerManager playerManager;
    private String linkPlay = "";
    private RelativeLayout rlRootView;
    private ImageButton exoFullscreen;
    private ImageButton exoMiniPlayer;
    private AVLoadingIndicatorView avl;
    private ImageButton exoBack;
    private TextView tvTitle;
    private BaseActivity activity;

    public UZVideo(Context context) {
        super(context);
        init();
    }

    public UZVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UZVideo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private boolean isConnectedFirst;

    private void init() {
        inflate(getContext(), R.layout.view_uz_video, this);
        activity = (BaseActivity) getContext();
        isConnectedFirst = LConnectivityUtil.isConnected(activity);
        //LLog.d(TAG, "isConnectedFirst " + isConnectedFirst);
        rlRootView = findViewById(R.id.rl_root_view);
        playerView = findViewById(R.id.player_view);
        exoFullscreen = (ImageButton) findViewById(R.id.exo_fullscreen);
        exoMiniPlayer = (ImageButton) findViewById(R.id.exo_mini_player);
        exoBack = (ImageButton) findViewById(R.id.exo_back);
        avl = (AVLoadingIndicatorView) findViewById(R.id.avl);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        if (tvTitle != null) {
            LUIUtil.setTextShadow(tvTitle);
        }

        playerManager = new PlayerManager(activity);
        playerManager.updateSizePlayerView(activity, playerView, exoFullscreen);
        exoFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerManager.toggleFullscreen(activity);
            }
        });
        exoMiniPlayer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showPip();
            }
        });
        exoBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBack();
            }
        });
    }

    public void onResume() {
        playerManager.resumeVideo();
    }

    public void onPause() {
        playerManager.pauseVideo();
    }

    public void toggleFullscreen() {
        playerManager.toggleFullscreen(activity);
    }

    public void onDestroy() {
        playerManager.release();
    }

    private boolean isLandscape;

    public boolean isLandscape() {
        return isLandscape;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandscape = true;
            if (exoMiniPlayer != null) {
                exoMiniPlayer.setVisibility(View.GONE);
            }
            if (playerManager != null) {
                playerManager.updateSizePlayerView(activity, playerView, exoFullscreen);
            }
            if (uzCallback != null) {
                uzCallback.onScreenRotateChange(true);
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            isLandscape = false;
            if (exoMiniPlayer != null) {
                exoMiniPlayer.setVisibility(View.VISIBLE);
            }
            if (playerManager != null) {
                playerManager.updateSizePlayerView(activity, playerView, exoFullscreen);
            }
            if (uzCallback != null) {
                uzCallback.onScreenRotateChange(false);
            }
        }
    }

    public void playUrl(String linkPlay) {
        ServiceUtils.stopService(FUZService.class);
        playerManager.release();
        showLoading();
        this.linkPlay = linkPlay;
        playerManager.init(activity, this, playerView, linkPlay);
        if (uzCallback != null) {
            uzCallback.onInitSuccess(linkPlay);
        }
    }

    public void playEntity(final String entityId) {
        ServiceUtils.stopService(FUZService.class);
        playerManager.release();
        showLoading();
        getTokenStreaming(entityId, new CallbackAPI() {
            @Override
            public void onSuccess(ResultGetTokenStreaming resultGetTokenStreaming, ResultGetLinkPlay resultGetLinkPlay) {
                try {
                    List<Url> urlList = resultGetLinkPlay.getData().getUrls();
                    for (int i = 0; i < urlList.size(); i++) {
                        if (urlList.get(i).getUrl().endsWith("mpd")) {
                            linkPlay = urlList.get(i).getUrl();
                        }
                    }
                    LLog.d(TAG, "linkPlay " + linkPlay);
                    playerManager.init(activity, UZVideo.this, playerView, linkPlay);
                    if (uzCallback != null) {
                        uzCallback.onInitSuccess(linkPlay);
                    }
                } catch (NullPointerException e) {
                    LLog.e(TAG, "Error NullPointerException " + e.toString());
                }
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "onFail play entityId " + entityId);
            }
        });
    }

    private void getTokenStreaming(final String entityId, final CallbackAPI callbackAPI) {
        UZService service = UZRestClient.createService(UZService.class);
        SendGetTokenStreaming sendGetTokenStreaming = new SendGetTokenStreaming();
        sendGetTokenStreaming.setAppId(UZData.getInstance().getAppId());
        sendGetTokenStreaming.setEntityId(entityId);
        sendGetTokenStreaming.setContentType(SendGetTokenStreaming.STREAM);
        activity.subscribe(service.getTokenStreaming(sendGetTokenStreaming), new ApiSubscriber<ResultGetTokenStreaming>() {
            @Override
            public void onSuccess(ResultGetTokenStreaming result) {
                //LLog.d(TAG, "getTokenStreaming onSuccess: " + LSApplication.getInstance().getGson().toJson(result));
                String tokenStreaming = result.getData().getToken();
                getLinkPlay(tokenStreaming, entityId, result, callbackAPI);
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "getTokenStreaming onFail " + e.getMessage());
                if (callbackAPI != null) {
                    callbackAPI.onFail(e);
                }
            }
        });
    }

    private void getLinkPlay(final String tokenStreaming, String entityId, final ResultGetTokenStreaming resultGetTokenStreaming, final CallbackAPI callbackAPI) {
        if (tokenStreaming == null || tokenStreaming.isEmpty()) {
            if (callbackAPI != null) {
                callbackAPI.onFail(new Throwable("No token streaming found"));
            }
            return;
        }
        UZRestClientGetLinkPlay.addAuthorization(tokenStreaming);
        UZService service = UZRestClientGetLinkPlay.createService(UZService.class);
        String typeContent = SendGetTokenStreaming.STREAM;
        activity.subscribe(service.getLinkPlay(UZData.getInstance().getAppId(), entityId, typeContent), new ApiSubscriber<ResultGetLinkPlay>() {
            @Override
            public void onSuccess(ResultGetLinkPlay resultGetLinkPlay) {
                //LLog.d(TAG, "getLinkPlay onSuccess: " + LSApplication.getInstance().getGson().toJson(resultGetLinkPlay));
                if (callbackAPI != null) {
                    callbackAPI.onSuccess(resultGetTokenStreaming, resultGetLinkPlay);
                }
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "getLinkPlay onFail " + e.getMessage());
                if (callbackAPI != null) {
                    callbackAPI.onFail(e);
                }
            }
        });
    }

    public interface CallbackAPI {
        public void onSuccess(ResultGetTokenStreaming resultGetTokenStreaming, ResultGetLinkPlay resultGetLinkPlay);

        public void onFail(Throwable e);
    }

    private UZCallback uzCallback;

    public void setUzCallback(UZCallback uzCallback) {
        this.uzCallback = uzCallback;
    }

    public interface UZCallback {
        public void onScreenRotateChange(boolean isLandscape);

        public void onInitSuccess(String linkPlay);
    }

    public void showLoading() {
        if (avl != null) {
            avl.smoothToShow();
        }
    }

    public void hideLoading() {
        if (avl != null) {
            avl.smoothToHide();
        }
    }

    public void onNetworkChange(EventBusData.ConnectEvent event) {
        if (event == null) {
            return;
        }
        if (isConnectedFirst) {
            isConnectedFirst = false;
            return;
        }
        //LLog.d(TAG, "onNetworkChange isConnected " + event.isConnected());
        if (event.isConnected()) {
            if (playerManager == null) {
                //LLog.d(TAG, "null");
            } else {
                //LLog.d(TAG, "play again");
                playerManager.reset();
                playerManager.init(activity, this, playerView, linkPlay);
            }
        }
    }

    public void hideController() {
        if (playerView != null) {
            playerView.hideController();
        }
    }

    public void showController() {
        if (playerView != null) {
            playerView.showController();
        }
    }

    public void setUseController(boolean useController) {
        if (playerView != null) {
            playerView.setUseController(useController);
        }
    }

    private void onClickBack() {
        if (isLandscape) {
            toggleFullscreen();
        } else {
            activity.onBackPressed();
        }
    }

    public void setTvTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public RelativeLayout getRlRootView() {
        return rlRootView;
    }

    private void showPip() {
        //TODO permssion overlay
        if (linkPlay == null || playerManager == null) {
            return;
        }
        Intent intent = new Intent(activity, FUZService.class);
        intent.putExtra(Constants.KEY_VIDEO_LINK_PLAY, linkPlay);
        intent.putExtra(Constants.KEY_VIDEO_CURRENT_POSITION, playerManager.getContentPosition());
        activity.startService(intent);
        activity.onBackPressed();
    }
}