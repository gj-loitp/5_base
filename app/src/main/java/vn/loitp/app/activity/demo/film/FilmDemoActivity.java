package vn.loitp.app.activity.demo.film;

import android.os.Bundle;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.film.group0.FrmGroup0;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.utils.util.FragmentUtils;

public class FilmDemoActivity extends BaseFontActivity {
    private LinearLayout llBaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        llBaseView = (LinearLayout) findViewById(R.id.ll_base_view);
        FragmentUtils.addFragment(getSupportFragmentManager(), new FrmGroup0(), llBaseView.getId(), false);
        FragmentUtils.addFragment(getSupportFragmentManager(), new FrmGroup0(), llBaseView.getId(), false);
        FragmentUtils.addFragment(getSupportFragmentManager(), new FrmGroup0(), llBaseView.getId(), false);
        FragmentUtils.addFragment(getSupportFragmentManager(), new FrmGroup0(), llBaseView.getId(), false);
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
        return R.layout.activity_film_demo;
    }
}
