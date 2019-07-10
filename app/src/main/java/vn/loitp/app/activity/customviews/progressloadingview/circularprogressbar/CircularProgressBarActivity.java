package vn.loitp.app.activity.customviews.progressloadingview.circularprogressbar;

import android.os.Bundle;

import com.core.base.BaseFontActivity;

import loitp.basemaster.R;
import vn.loitp.views.progressloadingview.circularprogressbar.lib.CircularProgressBar;

public class CircularProgressBarActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CircularProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setProgress(30f);
        progressBar.configure().animateProgress(true).maximum(40).progress(30).apply();
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
        return R.layout.activity_circular_progress_bar;
    }
}
