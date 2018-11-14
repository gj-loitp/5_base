package vn.loitp.app.activity.customviews.videoview.uzvideo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.restapi.uiza.UZRestClient;
import vn.loitp.restapi.uiza.UZRestClientGetLinkPlay;
import vn.loitp.restapi.uiza.UZService;
import vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay.ResultGetLinkPlay;
import vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay.Url;
import vn.loitp.restapi.uiza.model.v3.linkplay.gettokenstreaming.ResultGetTokenStreaming;
import vn.loitp.restapi.uiza.model.v3.linkplay.gettokenstreaming.SendGetTokenStreaming;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.exo.PlayerManager;

//custom UI exo_playback_control_view.xml
public class UZActivity extends BaseFontActivity {
    private PlayerView playerView;
    private PlayerManager playerManager;
    private String linkPlay = "";
    private ImageButton exoFullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        findViewById(R.id.bt_vod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(LSApplication.entityIdDefaultVOD, new Callback() {
                    @Override
                    public void onSuccess(ResultGetTokenStreaming resultGetTokenStreaming, ResultGetLinkPlay resultGetLinkPlay) {
                        //LLog.d(TAG, "resultGetLinkPlay " + LSApplication.getInstance().getGson().toJson(resultGetLinkPlay.getData().getUrls()));
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
                        LLog.d(TAG, "onFail " + e.toString());
                    }
                });
            }
        });
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
    protected int setLayoutResourceId() {
        return R.layout.activity_uz;
    }

    @Override
    public void onResume() {
        super.onResume();
        //playerManager.init(this, playerView, linkPlay);
        playerManager.resumeVideo();
    }

    @Override
    public void onPause() {
        super.onPause();
        //playerManager.reset();
        playerManager.pauseVideo();
    }

    @Override
    public void onDestroy() {
        playerManager.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (LScreenUtil.isFullScreen(activity)) {
            playerManager.toggleFullscreen(activity);
        } else {
            super.onBackPressed();
        }
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

    private void play(String entityId, Callback callback) {
        getTokenStreaming(entityId, callback);
    }

    private void getTokenStreaming(String entityId, Callback callback) {
        UZService service = UZRestClient.createService(UZService.class);
        SendGetTokenStreaming sendGetTokenStreaming = new SendGetTokenStreaming();
        sendGetTokenStreaming.setAppId(LSApplication.DF_APP_ID);
        sendGetTokenStreaming.setEntityId(entityId);
        sendGetTokenStreaming.setContentType(SendGetTokenStreaming.STREAM);
        subscribe(service.getTokenStreaming(sendGetTokenStreaming), new ApiSubscriber<ResultGetTokenStreaming>() {
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

    private void getLinkPlay(String tokenStreaming, String entityId, ResultGetTokenStreaming resultGetTokenStreaming, Callback callback) {
        if (tokenStreaming == null || tokenStreaming.isEmpty()) {
            if (callback != null) {
                callback.onFail(new Throwable("No token streaming found"));
            }
            return;
        }
        UZRestClientGetLinkPlay.addAuthorization(tokenStreaming);
        UZService service = UZRestClientGetLinkPlay.createService(UZService.class);
        String typeContent = SendGetTokenStreaming.STREAM;
        subscribe(service.getLinkPlay(LSApplication.DF_APP_ID, entityId, typeContent), new ApiSubscriber<ResultGetLinkPlay>() {
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
