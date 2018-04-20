package vn.loitp.views.uizavideo;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.rubensousa.previewseekbar.base.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;

import loitp.core.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.uizavideo.listerner.ProgressCallback;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmUizaIMAVideo extends BaseFragment implements PreviewView.OnPreviewChangeListener, View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private PlayerView playerView;
    private UizaPlayerManager uizaPlayerManager;

    //play controller
    private PreviewTimeBarLayout previewTimeBarLayout;
    private PreviewTimeBar previewTimeBar;
    private ImageButton exoFullscreenIcon;
    private ImageView ivThumbnail;
    private ProgressBar progressBar;
    private TextView tvTitle;
    private ImageButton exoBackScreen;
    private ImageButton exoVolume;
    private ImageButton exoSetting;
    private ImageButton exoCc;
    private ImageButton exoPlaylist;
    private ImageButton exoHearing;

    private LinearLayout debugRootView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViews(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        LUIUtil.setColorProgressBar(progressBar, ContextCompat.getColor(progressBar.getContext(), R.color.White));
        playerView = view.findViewById(R.id.player_view);
        previewTimeBar = playerView.findViewById(R.id.exo_progress);
        previewTimeBarLayout = playerView.findViewById(R.id.previewSeekBarLayout);
        previewTimeBarLayout.setTintColorResource(R.color.colorPrimary);
        previewTimeBar.addOnPreviewChangeListener(this);
        ivThumbnail = (ImageView) playerView.findViewById(R.id.imageView);
        exoFullscreenIcon = (ImageButton) playerView.findViewById(R.id.exo_fullscreen_icon);
        tvTitle = (TextView) playerView.findViewById(R.id.tv_title);
        exoBackScreen = (ImageButton) playerView.findViewById(R.id.exo_back_screen);
        exoVolume = (ImageButton) playerView.findViewById(R.id.exo_volume);
        exoSetting = (ImageButton) playerView.findViewById(R.id.exo_setting);
        exoCc = (ImageButton) playerView.findViewById(R.id.exo_cc);
        exoPlaylist = (ImageButton) playerView.findViewById(R.id.exo_playlist);
        exoHearing = (ImageButton) playerView.findViewById(R.id.exo_hearing);

        debugRootView = view.findViewById(R.id.controls_root);
        if (Constants.IS_DEBUG) {
            debugRootView.setVisibility(View.GONE);
        } else {
            debugRootView.setVisibility(View.GONE);
        }

        //onclick
        exoFullscreenIcon.setOnClickListener(this);
        exoBackScreen.setOnClickListener(this);
        exoVolume.setOnClickListener(this);
        exoSetting.setOnClickListener(this);
        exoCc.setOnClickListener(this);
        exoPlaylist.setOnClickListener(this);
        exoHearing.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uiza_ima_video_frm, container, false);
        findViews(view);
        UizaUtil.resizeLayout(playerView);

        initUI();
        return view;
    }

    public void initData(String linkPlay, String urlIMAAd, String urlThumnailsPreviewSeekbar) {
        uizaPlayerManager = new UizaPlayerManager(getActivity(), playerView,progressBar, previewTimeBarLayout, ivThumbnail, linkPlay, urlIMAAd, urlThumnailsPreviewSeekbar);
        previewTimeBarLayout.setPreviewLoader(uizaPlayerManager);
        uizaPlayerManager.setProgressCallback(new ProgressCallback() {
            @Override
            public void onAdProgress(float currentMls, float duration, int percent) {
                LLog.d(TAG, "ad progress: " + currentMls + "/" + duration + " -> " + percent + "%");
            }

            @Override
            public void onVideoProgress(float currentMls, float duration, int percent) {
                LLog.d(TAG, "video progress: " + currentMls + "/" + duration + " -> " + percent + "%");
            }
        });
        uizaPlayerManager.setDebugCallback(new UizaPlayerManager.DebugCallback() {
            @Override
            public void onUpdateButtonVisibilities() {
                LLog.d(TAG, "onUpdateButtonVisibilities");
                updateButtonVisibilities();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        uizaPlayerManager.init();
    }

    @Override
    public void onPause() {
        super.onPause();
        uizaPlayerManager.reset();
    }

    @Override
    public void onDestroy() {
        uizaPlayerManager.release();
        super.onDestroy();
    }

    @Override
    public void onStartPreview(PreviewView previewView) {
        LLog.d(TAG, "onStartPreview");
    }

    @Override
    public void onStopPreview(PreviewView previewView) {
        LLog.d(TAG, "onStopPreview");
        uizaPlayerManager.resumeVideo();
    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {
        LLog.d(TAG, "onPreview progress " + progress);
    }

    @Override
    public void onClick(View v) {
        if (v == exoFullscreenIcon) {
            UizaUtil.setUIFullScreenIcon(getActivity(), exoFullscreenIcon);
            LActivityUtil.toggleScreenOritation(getActivity());
        } else if (v == exoBackScreen) {
            if (isLandscape) {
                exoFullscreenIcon.performClick();
            } else {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        } else if (v == exoVolume) {
            uizaPlayerManager.toggleVolumeMute(exoVolume);
        } else if (v == exoSetting) {
            View view = UizaUtil.getBtVideo(debugRootView);
            if (view != null) {
                UizaUtil.getBtVideo(debugRootView).performClick();
            }
        } else if (v == exoCc) {
            View view = UizaUtil.getBtText(debugRootView);
            if (view != null) {
                UizaUtil.getBtText(debugRootView).performClick();
            }
        } else if (v == exoPlaylist) {
            //TODO
            LToast.show(getActivity(), "exoPlaylist");
        } else if (v == exoHearing) {
            View view = UizaUtil.getBtAudio(debugRootView);
            if (view != null) {
                UizaUtil.getBtAudio(debugRootView).performClick();
            }
        } else if (v.getParent() == debugRootView) {
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = uizaPlayerManager.getTrackSelector().getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                uizaPlayerManager.getTrackSelectionHelper().showSelectionDialog(getActivity(), ((Button) v).getText(), mappedTrackInfo, (int) v.getTag());
            }
        }
    }

    private boolean isLandscape;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getActivity() != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LScreenUtil.hideDefaultControls(getActivity());
                isLandscape = true;
            } else {
                LScreenUtil.showDefaultControls(getActivity());
                isLandscape = false;
            }
        }
        UizaUtil.resizeLayout(playerView);
    }

    public void initUI() {
        String title = "Dummy Video";

        updateUIPlayController(title);
    }

    private void updateUIPlayController(String title) {
        tvTitle.setText(title);
        LUIUtil.setTextShadow(tvTitle);
    }

    public void updateButtonVisibilities() {
        debugRootView.removeAllViews();
        if (uizaPlayerManager.getPlayer() == null) {
            return;
        }
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = uizaPlayerManager.getTrackSelector().getCurrentMappedTrackInfo();
        if (mappedTrackInfo == null) {
            return;
        }
        for (int i = 0; i < mappedTrackInfo.length; i++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
            if (trackGroups.length != 0) {
                Button button = new Button(context);
                int label;
                switch (uizaPlayerManager.getPlayer().getRendererType(i)) {
                    case C.TRACK_TYPE_AUDIO:
                        label = R.string.audio;
                        break;
                    case C.TRACK_TYPE_VIDEO:
                        label = R.string.video;
                        break;
                    case C.TRACK_TYPE_TEXT:
                        label = R.string.text;
                        break;
                    default:
                        continue;
                }
                button.setText(label);
                button.setTag(i);
                button.setOnClickListener(this);
                debugRootView.addView(button);
            }
        }
    }
}