package vn.loitp.app.activity.customviews.videoview.uizavideowithima;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.rubensousa.previewseekbar.base.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.videoview.uizavideo.UizaUtil;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;

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
        playerView = view.findViewById(R.id.player_view);
        previewTimeBar = playerView.findViewById(R.id.exo_progress);
        previewTimeBarLayout = playerView.findViewById(R.id.previewSeekBarLayout);
        previewTimeBarLayout.setTintColorResource(R.color.colorPrimary);
        previewTimeBar.addOnPreviewChangeListener(this);
        ivThumbnail = (ImageView) playerView.findViewById(R.id.imageView);
        playerView.findViewById(R.id.exo_fullscreen_button).setOnClickListener(this);
        exoFullscreenIcon = (ImageView) playerView.findViewById(R.id.exo_fullscreen_icon);

        init(getString(R.string.ad_tag_url), getString(R.string.url_thumbnails));

        UizaUtil.resizeLayout(playerView);
        return view;
    }

    public void init(String urlIMAAd, String urlThumnailsPreviewSeekbar) {
        String linkPlay = context.getString(R.string.url_dash);
        uizaPlayerManager = new UizaPlayerManager(getActivity(), previewTimeBarLayout, ivThumbnail, linkPlay, urlIMAAd, urlThumnailsPreviewSeekbar);
        previewTimeBarLayout.setPreviewLoader(uizaPlayerManager);
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
    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {
        LLog.d(TAG, "onPreview progress " + progress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exo_fullscreen_button:
                UizaUtil.setUIFullScreenIcon(getActivity(), exoFullscreenIcon);
                LActivityUtil.toggleScreenOritation(getActivity());
                break;
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