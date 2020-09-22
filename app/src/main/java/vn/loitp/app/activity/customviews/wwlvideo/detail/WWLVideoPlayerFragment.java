package vn.loitp.app.activity.customviews.wwlvideo.detail;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFragment;
import com.views.LToast;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.wwlvideo.interfaces.FragmentHost;
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset;

/**
 * Created by loitp on 2/26/17.
 */

@LayoutId(R.layout.wwl_video_player_fragment)
@LogTag("DatabaseFirebaseSignInActivity")
public class WWLVideoPlayerFragment extends BaseFragment {
    private String mUrl;
    private FragmentHost mFragmentHost;
    private String entityIdDefaultVOD = "7699e10e-5ce3-4dab-a5ad-a615a711101e";
    private String entityIdDefaultLIVE = "1759f642-e062-4e88-b5f2-e3022bd03b57";

    @Nullable

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mFragmentHost = (FragmentHost) activity;
    }

    public void startPlay(WWLVideoDataset.DatasetItem item) {
        this.mUrl = item.url;

    }

    public void stopPlay() {

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
    }

    public void hideControls() {
    }

    public void toggleControls() {
        LToast.showShort(getActivity(), "Loitp toggleControls");
        /*if (this.mPlayerWWLVideoControlsOverlay != null) {
            this.mPlayerWWLVideoControlsOverlay.toggleControls();
        }*/
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