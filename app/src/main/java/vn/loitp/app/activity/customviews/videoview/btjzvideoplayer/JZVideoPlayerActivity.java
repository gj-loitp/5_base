package vn.loitp.app.activity.customviews.videoview.btjzvideoplayer;

import android.app.Activity;
import android.os.Bundle;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;

//TODO remove in gradle
//guide https://android-arsenal.com/details/1/3269
public class JZVideoPlayerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        jzVideoPlayerStandard
                .setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                //.setUp("http://www.hoahaomedia.org/hoahao/_media/PGHH/Audio/Sam_Giang/Hue_Duyen/001_Sam%20Giang%20Khuyen%20Nguoi%20Doi%20Tu%20Niem%20Hue%20Duyen%20-%20Tri%20Tung.mp3"
                        , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL
                        , "Title of video");
        //jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        //jzVideoPlayerStandard.thumbImageView.setImageResource(R.drawable.iv);
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
        return R.layout.activity_jzvideo_player;
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
