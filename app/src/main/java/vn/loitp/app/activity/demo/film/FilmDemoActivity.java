package vn.loitp.app.activity.demo.film;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.film.group0.FrmGroup0;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.utils.util.FragmentUtils;

public class FilmDemoActivity extends BaseFontActivity implements View.OnClickListener {
    private LinearLayout llBaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        llBaseView = (LinearLayout) findViewById(R.id.ll_base_view);
        findViewById(R.id.bt_add_group_0).setOnClickListener(this);
        findViewById(R.id.bt_remove_group_0).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_group_0:
                add();
                break;
            case R.id.bt_remove_group_0:
                break;
        }
    }

    private void add() {
        FragmentUtils.addFragment(getSupportFragmentManager(), new FrmGroup0(), llBaseView.getId(), false);
    }
}
