package vn.loitp.app.activity.customviews.videoview.youtube;

import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;

import loitp.basemaster.R;

public class YoutubeActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LUIUtil.INSTANCE.playYoutube(getActivity(), "https://www.youtube.com/watch?v=YE7VzlLtp-4&ab_channel=Blender");
            }
        });
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LUIUtil.INSTANCE.playYoutubeWithId(getActivity(), "YE7VzlLtp-4");
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
