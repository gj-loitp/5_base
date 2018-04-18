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
import android.widget.ImageView;
import android.widget.ProgressBar;

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
    private PreviewTimeBarLayout previewTimeBarLayout;
    private PreviewTimeBar previewTimeBar;
    private ImageView exoFullscreenIcon;
    private ImageView ivThumbnail;
    private ProgressBar progressBar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uiza_ima_video_frm, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        LUIUtil.setColorProgressBar(progressBar, ContextCompat.getColor(progressBar.getContext(), R.color.White));
        playerView = view.findViewById(R.id.player_view);
        previewTimeBar = playerView.findViewById(R.id.exo_progress);
        previewTimeBarLayout = playerView.findViewById(R.id.previewSeekBarLayout);
        previewTimeBarLayout.setTintColorResource(R.color.colorPrimary);
        previewTimeBar.addOnPreviewChangeListener(this);
        ivThumbnail = (ImageView) playerView.findViewById(R.id.imageView);
        playerView.findViewById(R.id.exo_fullscreen_button).setOnClickListener(this);
        exoFullscreenIcon = (ImageView) playerView.findViewById(R.id.exo_fullscreen_icon);

        UizaUtil.resizeLayout(playerView);

        /*SubtitleView subtitleView = playerView.getSubtitleView();
        subtitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LToast.show(getActivity(), "Touch subtitle");
            }
        });*/

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
        int id = v.getId();
        if (id == R.id.exo_fullscreen_button) {
            UizaUtil.setUIFullScreenIcon(getActivity(), exoFullscreenIcon);
            LActivityUtil.toggleScreenOritation(getActivity());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getActivity() != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LScreenUtil.hideDefaultControls(getActivity());
            } else {
                LScreenUtil.showDefaultControls(getActivity());
            }
        }
        UizaUtil.resizeLayout(playerView);
    }
}