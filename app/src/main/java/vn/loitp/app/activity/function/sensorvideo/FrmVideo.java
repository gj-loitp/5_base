package vn.loitp.app.activity.function.sensorvideo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.exoplayer2.ui.PlayerView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;

/**
 * Created by LENOVO on 6/22/2018.
 */

public class FrmVideo extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private PlayerView playerView;
    private PlayerManager player;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_sensor_video;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playerView = frmRootView.findViewById(R.id.player_view);
        player = new PlayerManager(getActivity());
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
}