package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview.ParallaxAdapter;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.recyclerview.parallaxrecyclerviewyayandroid.ParallaxRecyclerView;

public class ParallaxYayandroidRecyclerViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParallaxRecyclerView recyclerView = (ParallaxRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new TestRecyclerAdapter(this));

        LUIUtil.setPullLikeIOSVertical(recyclerView);
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
        return R.layout.activity_parallax_recycler_view_yayandroid;
    }
}
