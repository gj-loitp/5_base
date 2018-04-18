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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.rubensousa.previewseekbar.base.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import loitp.core.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
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
        //onclick
        exoFullscreenIcon.setOnClickListener(this);
        exoBackScreen.setOnClickListener(this);
        exoVolume.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uiza_ima_video_frm, container, false);
        findViews(view);
        UizaUtil.resizeLayout(playerView);

        initUI();
        return view;
    }

    public void init(String linkPlay, String urlIMAAd, String urlThumnailsPreviewSeekbar) {
        uizaPlayerManager = new UizaPlayerManager(getActivity(), progressBar, previewTimeBarLayout, ivThumbnail, linkPlay, urlIMAAd, urlThumnailsPreviewSeekbar);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        uizaPlayerManager.init(getActivity(), playerView);
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
}