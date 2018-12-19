package vn.loitp.app.activity.demo.uiza;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.data.EventBusData;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;
import vn.loitp.views.uzvideo.UZVideo;

//custom UI exo_playback_control_view.xml
public class UZPlayerActivity extends BaseFontActivity {
    private UZVideo uzVideo;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uzVideo = (UZVideo) findViewById(R.id.uz_video);
        data = (Data) getIntent().getSerializableExtra(UZCons.ENTITY_DATA);
        if (data == null) {
            showDialogError(getString(R.string.video_not_found));
            return;
        }
        String entityId = data.getId();
        if (entityId == null || entityId.isEmpty()) {
            showDialogError(getString(R.string.video_not_found));
            return;
        }
        uzVideo.setTvTitle(data.getName() + "");
        uzVideo.playEntity(entityId);
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
        return R.layout.activity_uz_player;
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

    @Override
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
    }
}
