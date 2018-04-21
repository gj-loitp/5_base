package vn.loitp.app.activity.customviews.progressloadingview.circularprogressbar;

import android.app.Activity;
import android.os.Bundle;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.views.progressloadingview.circularprogressbar.lib.CircularProgressBar;

public class CircularProgressBarActivity extends BaseActivity {

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
