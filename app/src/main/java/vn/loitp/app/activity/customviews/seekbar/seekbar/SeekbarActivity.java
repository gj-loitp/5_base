package vn.loitp.app.activity.customviews.seekbar.seekbar;

import android.os.Bundle;
import android.widget.SeekBar;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;

import loitp.basemaster.R;

public class SeekbarActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SeekBar sb = (SeekBar) findViewById(R.id.sb);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LLog.INSTANCE.d(getTAG(), "onProgressChanged " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                LLog.INSTANCE.d(getTAG(), "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LLog.INSTANCE.d(getTAG(), "onStopTrackingTouch");
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
        return R.layout.activity_seekbar;
    }

}
