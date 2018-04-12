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
import vn.loitp.core.utilities.LScreenUtil;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmUizaIMAVideo extends BaseFragment implements PreviewView.OnPreviewChangeListener, View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private PlayerView playerView;
    private UizaPlayerManager player;
    private PreviewTimeBarLayout previewTimeBarLayout;
    private PreviewTimeBar previewTimeBar;
    private ImageView exoFullscreenIcon;

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

        ImageView ivThumbnail = (ImageView) playerView.findViewById(R.id.imageView);

        player = new UizaPlayerManager(getActivity(), previewTimeBarLayout, ivThumbnail, getString(R.string.url_thumbnails));
        previewTimeBarLayout.setPreviewLoader(player);

        playerView.findViewById(R.id.exo_fullscreen_button).setOnClickListener(this);
        exoFullscreenIcon = (ImageView) playerView.findViewById(R.id.exo_fullscreen_icon);

        UizaUtil.resizeLayout(playerView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        player.init(getActivity(), playerView);
    }

    @Override
    public void onPause() {
        super.onPause();
        player.reset();
    }

    @Override
    public void onDestroy() {
        player.release();
        super.onDestroy();
    }

    @Override
    public void onStartPreview(PreviewView previewView) {
        //
    }

    @Override
    public void onStopPreview(PreviewView previewView) {
        //
    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {
        //
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