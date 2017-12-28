package vn.loitp.app.activity.customviews.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.RecyclerViewActivity;
import vn.loitp.app.activity.customviews.recyclerview.normalwithspansize.RecyclerViewWithSpanSizeActivity;
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview.ParallaxRecyclerViewActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LUIUtil;

public class RecyclerViewMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_parallax_recyclerview).setOnClickListener(this);
        findViewById(R.id.bt_normal_recyclerview).setOnClickListener(this);
        findViewById(R.id.bt_normal_recyclerview_with_spansize).setOnClickListener(this);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_recyclerview;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_parallax_recyclerview:
                intent = new Intent(activity, ParallaxRecyclerViewActivity.class);
                break;
            case R.id.bt_normal_recyclerview:
                intent = new Intent(activity, RecyclerViewActivity.class);
                break;
            case R.id.bt_normal_recyclerview_with_spansize:
                intent = new Intent(activity, RecyclerViewWithSpanSizeActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LUIUtil.transActivityFadeIn(activity);
        }
    }
}
