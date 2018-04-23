package vn.loitp.app.activity.customviews.videoview.uizavideo;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.rubensousa.previewseekbar.base.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.videoview.uizavideo.uizaplayer.ExoPlayerManagerPB;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.uizavideo.view.util.UizaUtil;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmUizaVideo extends BaseFragment implements PreviewView.OnPreviewChangeListener, View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private ExoPlayerManagerPB exoPlayerManagerPB;
    private PreviewTimeBarLayout previewTimeBarLayout;
    private PreviewTimeBar previewTimeBar;
    private ImageView imgThumnailSeekbar;
    private SimpleExoPlayerView simpleExoPlayerView;

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
        View view = inflater.inflate(R.layout.uiza_video_frm, container, false);
        simpleExoPlayerView = view.findViewById(R.id.player_view);
        previewTimeBar = simpleExoPlayerView.findViewById(R.id.exo_progress);
        previewTimeBarLayout = simpleExoPlayerView.findViewById(R.id.previewSeekBarLayout);
        imgThumnailSeekbar = (ImageView) simpleExoPlayerView.findViewById(R.id.imageView);
        previewTimeBarLayout.setTintColorResource(R.color.colorPrimary);
        previewTimeBar.addOnPreviewChangeListener(this);
        simpleExoPlayerView.findViewById(R.id.exo_fullscreen_icon).setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.onDestroy();
        }
        super.onDestroy();
    }

    /*@Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_toggle) {
            if (previewTimeBarLayout.isShowingPreview()) {
                previewTimeBarLayout.hidePreview();
                exoPlayerManagerPB.stopPreview();
            } else {
                previewTimeBarLayout.showPreview();
                exoPlayerManagerPB.loadPreview(previewTimeBar.getProgress(),
                        previewTimeBar.getDuration());
            }
        }
        return true;
    }*/

    @Override
    public void onStartPreview(PreviewView previewView) {
        LLog.d(TAG, "onStartPreview");
    }

    @Override
    public void onStopPreview(PreviewView previewView) {
        LLog.d(TAG, "onStopPreview");
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.stopPreview();
        }
    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {
        LLog.d(TAG, "onPreview " + progress);
    }

    public void play(String linkPlay, String linkThumnails) {
        LLog.d(TAG, "play " + linkPlay);
        LLog.d(TAG, simpleExoPlayerView == null ? "simpleExoPlayerView null" : "simpleExoPlayerView !null");
        exoPlayerManagerPB = new ExoPlayerManagerPB(simpleExoPlayerView, previewTimeBarLayout, imgThumnailSeekbar, linkThumnails);
        exoPlayerManagerPB.play(Uri.parse(getString(R.string.url_dash)));
        //exoPlayerManagerPB.play(Uri.parse(getString(R.string.url_smooth)));
        //exoPlayerManagerPB.play(Uri.parse(getString(R.string.url_hls)));
        //exoPlayerManagerPB.play(Uri.parse(getString(R.string.url_mp3)));
        exoPlayerManagerPB.play(Uri.parse(linkPlay));
        previewTimeBarLayout.setPreviewLoader(exoPlayerManagerPB);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exo_fullscreen_icon:
                UizaUtil.setUIFullScreenIcon(getActivity(), (ImageButton) v);
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
    }

    public SimpleExoPlayerView getSimpleExoPlayerView() {
        return simpleExoPlayerView;
    }

    public SimpleExoPlayer getSimpleExoPlayer() {
        return (SimpleExoPlayer) simpleExoPlayerView.getPlayer();
    }
}