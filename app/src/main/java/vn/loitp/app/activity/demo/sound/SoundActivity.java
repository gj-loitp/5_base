package vn.loitp.app.activity.demo.sound;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LSoundUtil;

public class SoundActivity extends BaseFontActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_play).setOnClickListener(this);
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
        return R.layout.activity_sound;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                LSoundUtil.startMusicFromAsset(activity, "ting.ogg");
                break;
        }
    }
}
