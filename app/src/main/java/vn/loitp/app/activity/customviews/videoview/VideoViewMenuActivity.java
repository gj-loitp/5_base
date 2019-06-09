package vn.loitp.app.activity.customviews.videoview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.videoview.exoplayer.ExoPlayerActivity;
import vn.loitp.app.activity.customviews.videoview.exoplayer.ExoPlayerActivity2;
import vn.loitp.app.activity.customviews.videoview.exoplayer.ExoPlayerActivity3;
import vn.loitp.app.activity.customviews.videoview.youtube.YoutubeActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LActivityUtil;

public class VideoViewMenuActivity extends BaseFontActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_exoplayer2).setOnClickListener(this);
        findViewById(R.id.bt_exoplayer2_ima).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
        findViewById(R.id.bt_youtube).setOnClickListener(this);
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
        return R.layout.activity_menu_video_view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_exoplayer2:
                intent = new Intent(activity, ExoPlayerActivity.class);
                intent.putExtra(Constants.INSTANCE.getKEY_VIDEO_LINK_PLAY(), "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd");
                break;
            case R.id.bt_exoplayer2_ima:
                intent = new Intent(activity, ExoPlayerActivity.class);
                intent.putExtra(Constants.INSTANCE.getKEY_VIDEO_LINK_PLAY(), "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd");
                intent.putExtra(Constants.INSTANCE.getKEY_VIDEO_LINK_IMA_AD(), getString(R.string.ad_tag_url));
                break;
            case R.id.bt_2:
                intent = new Intent(activity, ExoPlayerActivity2.class);
                break;
            case R.id.bt_3:
                intent = new Intent(activity, ExoPlayerActivity3.class);
                break;
            case R.id.bt_youtube:
                intent = new Intent(activity, YoutubeActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
