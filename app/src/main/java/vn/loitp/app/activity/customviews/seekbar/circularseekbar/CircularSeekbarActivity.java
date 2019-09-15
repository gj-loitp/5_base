package vn.loitp.app.activity.customviews.seekbar.circularseekbar;

import android.graphics.Color;
import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.google.android.material.snackbar.Snackbar;
import com.views.seekbar.circular.LCircularSeekBar;

import java.text.DecimalFormat;

import loitp.basemaster.R;

//https://github.com/akaita/CircularSeekBar
public class CircularSeekbarActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LCircularSeekBar seekBar = (LCircularSeekBar) findViewById(R.id.seekbar);
        seekBar.setProgressTextFormat(new DecimalFormat("###,###,###,##0.00"));
        seekBar.setProgress(0);
        seekBar.setRingColor(Color.DKGRAY);

        seekBar.setOnCenterClickedListener(new LCircularSeekBar.OnCenterClickedListener() {
            @Override
            public void onCenterClicked(LCircularSeekBar seekBar, float progress) {
                Snackbar.make(seekBar, "Reset", Snackbar.LENGTH_SHORT).show();
                seekBar.setProgress(0);
            }
        });
        seekBar.setOnCircularSeekBarChangeListener(new LCircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(LCircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 20) {
                    seekBar.setRingColor(Color.GRAY);
                } else if (progress < 40) {
                    seekBar.setRingColor(Color.BLUE);
                } else if (progress < 60) {
                    seekBar.setRingColor(Color.GREEN);
                } else if (progress < 80) {
                    seekBar.setRingColor(Color.YELLOW);
                } else {
                    seekBar.setRingColor(Color.RED);
                }
            }

            @Override
            public void onStartTrackingTouch(LCircularSeekBar seekBar) {
                // Nothing
            }

            @Override
            public void onStopTrackingTouch(LCircularSeekBar seekBar) {
                // Nothing
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
        return R.layout.activity_circular_seekbar;
    }
}
