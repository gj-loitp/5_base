package vn.loitp.app.activity.customviews.videoview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.utilities.LActivityUtil;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.videoview.exoplayer.ExoPlayerActivity;
import vn.loitp.app.activity.customviews.videoview.exoplayer.ExoPlayerActivity2;
import vn.loitp.app.activity.customviews.videoview.exoplayer.ExoPlayerActivity3;
import vn.loitp.app.activity.customviews.videoview.youtube.YoutubeActivity;

public class VideoViewMenuActivity extends BaseFontActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.btExoPlayer2).setOnClickListener(this);
        findViewById(R.id.btExoPlayer2IMA).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
        findViewById(R.id.btYoutube).setOnClickListener(this);
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
        return R.layout.activity_video_menu;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btExoPlayer2:
                intent = new Intent(getActivity(), ExoPlayerActivity.class);
                intent.putExtra(Constants.INSTANCE.getKEY_VIDEO_LINK_PLAY(), "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd");
                break;
            case R.id.btExoPlayer2IMA:
                intent = new Intent(getActivity(), ExoPlayerActivity.class);
                intent.putExtra(Constants.INSTANCE.getKEY_VIDEO_LINK_PLAY(), "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd");
                intent.putExtra(Constants.INSTANCE.getKEY_VIDEO_LINK_IMA_AD(), getString(R.string.ad_tag_url));
                break;
            case R.id.bt2:
                intent = new Intent(getActivity(), ExoPlayerActivity2.class);
                break;
            case R.id.bt3:
                intent = new Intent(getActivity(), ExoPlayerActivity3.class);
                break;
            case R.id.btYoutube:
                intent = new Intent(getActivity(), YoutubeActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(getActivity());
        }
    }
}
