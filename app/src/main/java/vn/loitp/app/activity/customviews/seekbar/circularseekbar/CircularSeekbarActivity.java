package vn.loitp.app.activity.customviews.seekbar.circularseekbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import java.text.DecimalFormat;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.seekbar.circularseekbar.CircularSeekBar;
//https://github.com/akaita/CircularSeekBar
public class CircularSeekbarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CircularSeekBar seekBar = (CircularSeekBar) findViewById(R.id.seekbar);
        seekBar.setProgressTextFormat(new DecimalFormat("###,###,###,##0.00"));
        seekBar.setProgress(0);
        seekBar.setRingColor(Color.DKGRAY);

        seekBar.setOnCenterClickedListener(new CircularSeekBar.OnCenterClickedListener() {
            @Override
            public void onCenterClicked(CircularSeekBar seekBar, float progress) {
                Snackbar.make(seekBar, "Reset", Snackbar.LENGTH_SHORT).show();
                seekBar.setProgress(0);
            }
        });
        seekBar.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
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
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                // Nothing
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
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
