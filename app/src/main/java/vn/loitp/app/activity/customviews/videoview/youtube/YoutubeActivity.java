package vn.loitp.app.activity.customviews.videoview.youtube;

import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LUIUtil;

public class YoutubeActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LUIUtil.playYoutube(activity, "https://www.youtube.com/watch?v=YE7VzlLtp-4&ab_channel=Blender");
            }
        });
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LUIUtil.playYoutubeWithId(activity, "YE7VzlLtp-4");
            }
        });
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
        return R.layout.activity_youtube;
    }
}
