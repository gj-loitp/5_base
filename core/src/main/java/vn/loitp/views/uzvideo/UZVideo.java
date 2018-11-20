package vn.loitp.views.uzvideo;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.data.UZData;
import vn.loitp.restapi.uiza.UZRestClient;
import vn.loitp.restapi.uiza.UZRestClientGetLinkPlay;
import vn.loitp.restapi.uiza.UZService;
import vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay.ResultGetLinkPlay;
import vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay.Url;
import vn.loitp.restapi.uiza.model.v3.linkplay.gettokenstreaming.ResultGetTokenStreaming;
import vn.loitp.restapi.uiza.model.v3.linkplay.gettokenstreaming.SendGetTokenStreaming;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.exo.PlayerManager;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class UZVideo extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private PlayerView playerView;
    private PlayerManager playerManager;
    private String linkPlay = "";
    private ImageButton exoFullscreen;
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

    private void init() {
        inflate(getContext(), R.layout.view_uz_video, this);
        activity = (BaseActivity) getContext();
        playerView = findViewById(R.id.player_view);
        exoFullscreen = (ImageButton) findViewById(R.id.exo_fullscreen);

        playerManager = new PlayerManager(activity);
        playerManager.updateSizePlayerView(activity, playerView, exoFullscreen);
        exoFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerManager.toggleFullscreen(activity);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerManager.updateSizePlayerView(activity, playerView, exoFullscreen);
        } else {
            playerManager.updateSizePlayerView(activity, playerView, exoFullscreen);
        }
    }

    public void playUrl(String linkPlay) {
        playerManager.release();
        this.linkPlay = linkPlay;
        playerManager.init(activity, playerView, linkPlay);
    }

    public void playEntity(final String entityId) {
        playerManager.release();
        getTokenStreaming(entityId, new Callback() {
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
                    playerManager.init(activity, playerView, linkPlay);
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

    private void getTokenStreaming(final String entityId, final Callback callback) {
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
                getLinkPlay(tokenStreaming, entityId, result, callback);
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "getTokenStreaming onFail " + e.getMessage());
                if (callback != null) {
                    callback.onFail(e);
                }
            }
        });
    }

    private void getLinkPlay(final String tokenStreaming, String entityId, final ResultGetTokenStreaming resultGetTokenStreaming, final Callback callback) {
        if (tokenStreaming == null || tokenStreaming.isEmpty()) {
            if (callback != null) {
                callback.onFail(new Throwable("No token streaming found"));
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
                if (callback != null) {
                    callback.onSuccess(resultGetTokenStreaming, resultGetLinkPlay);
                }
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "getLinkPlay onFail " + e.getMessage());
                if (callback != null) {
                    callback.onFail(e);
                }
            }
        });
    }

    public interface Callback {
        public void onSuccess(ResultGetTokenStreaming resultGetTokenStreaming, ResultGetLinkPlay resultGetLinkPlay);

        public void onFail(Throwable e);
    }
}