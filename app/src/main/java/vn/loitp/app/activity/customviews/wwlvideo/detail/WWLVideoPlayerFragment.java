package vn.loitp.app.activity.customviews.wwlvideo.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.wwlvideo.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.views.LToast;
import vn.loitp.views.uzvideo.UZVideo;

/**
 * Created by loitp on 2/26/17.
 */

public class WWLVideoPlayerFragment extends BaseFragment {
    private String mUrl;
    private FragmentHost mFragmentHost;
    private UZVideo uzVideo;
    private String entityIdDefaultVOD = "7699e10e-5ce3-4dab-a5ad-a615a711101e";
    private String entityIdDefaultLIVE = "1759f642-e062-4e88-b5f2-e3022bd03b57";

    @Nullable

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uzVideo = (UZVideo) view.findViewById(R.id.uz_video);
        uzVideo.setUseController(false);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.wwl_video_player_fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mFragmentHost = (FragmentHost) activity;
    }

    public void startPlay(WWLVideoDataset.DatasetItem item) {
        this.mUrl = item.url;
        //uzVideo.playUrl(mUrl);
        uzVideo.playUrl("https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd");
        //uzVideo.playEntity(entityIdDefaultVOD);
    }

    public void stopPlay() {
        if (this.mUrl != null && uzVideo != null) {
            uzVideo.onDestroy();
        }
    }

    /*@Override
    public void CO_doCollapse() {
        if (this.mFragmentHost != null) {
            this.mFragmentHost.onVideoCollapse();
        }
    }

    @Override
    public void CO_doFullscreen(boolean selected) {
        if (this.mFragmentHost != null) {
            this.mFragmentHost.onVideoFullscreen(selected);
        }
    }*/

    public void switchFullscreen(boolean selected) {
        LToast.showShort(getActivity(), "Loitp switchFullscreen " + selected);
        /*if (this.mPlayerWWLVideoControlsOverlay != null) {
            this.mPlayerWWLVideoControlsOverlay.switchFullscreen(selected);
        }*/
        uzVideo.toggleFullscreen();
    }

    public void hideControls() {
        uzVideo.hideController();
    }

    public void toggleControls() {
        LToast.showShort(getActivity(), "Loitp toggleControls");
        /*if (this.mPlayerWWLVideoControlsOverlay != null) {
            this.mPlayerWWLVideoControlsOverlay.toggleControls();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        uzVideo.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uzVideo.onPause();
    }

    @Override
    public void onDestroy() {
        uzVideo.onDestroy();
        super.onDestroy();
    }

    /*@Override
    public void onBackPressed() {
        if (LScreenUtil.isFullScreen(activity)) {
            uzVideo.toggleFullscreen();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNetworkChange(EventBusData.ConnectEvent event) {
        super.onNetworkChange(event);
        //LLog.d(TAG, "onNetworkChange isConnected " + event.isConnected());
        if (uzVideo != null) {
            uzVideo.onNetworkChange(event);
        }
    }*/
}
