package vn.loitp.app.activity.customviews.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.RecyclerViewActivity;
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview.ParallaxRecyclerViewActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LUIUtil;

public class RecyclerViewMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_parallax_recyclerview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ParallaxRecyclerViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_normal_recyclerview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, RecyclerViewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_recyclerview;
    }
}
