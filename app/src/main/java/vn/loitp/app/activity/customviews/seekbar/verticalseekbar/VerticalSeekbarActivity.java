package vn.loitp.app.activity.customviews.seekbar.verticalseekbar;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.views.LToast;
import vn.loitp.views.seekbar.verticalseekbar.VerticalSeekBar;

public class VerticalSeekbarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VerticalSeekBar verticalSeekBar = (VerticalSeekBar) findViewById(R.id.seekbar);
        TextView tv = (TextView) findViewById(R.id.tv);
        verticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText(progress + "/" + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                LToast.show(activity, "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LToast.show(activity, "onStopTrackingTouch");
            }
        });
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verticalSeekBar.setProgress(50);
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
