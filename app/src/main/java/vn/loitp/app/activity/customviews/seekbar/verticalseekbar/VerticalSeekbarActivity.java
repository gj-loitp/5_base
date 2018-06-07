package vn.loitp.app.activity.customviews.seekbar.verticalseekbar;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.seekbar.verticalseekbar.VerticalSeekBar;

//https://github.com/h6ah4i/android-verticalseekbar
public class VerticalSeekbarActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final VerticalSeekBar seekBar1 = (VerticalSeekBar) findViewById(R.id.seekBar1);
        seekBar1.setMax(100);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LLog.d(TAG, "onProgressChanged " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                LLog.d(TAG, "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LLog.d(TAG, "onStopTrackingTouch");
            }
        });
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar1.setProgress(30);
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
        return R.layout.activity_vertical_seekbar;
    }
}
