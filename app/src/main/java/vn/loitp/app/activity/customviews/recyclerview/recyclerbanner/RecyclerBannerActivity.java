package vn.loitp.app.activity.customviews.recyclerview.recyclerbanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.RecyclerViewActivity;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.RecyclerViewWithSingletonDataActivity;
import vn.loitp.app.activity.customviews.recyclerview.normalwithspansize.RecyclerViewWithSpanSizeActivity;
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview.ParallaxRecyclerViewActivity;
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid.ParallaxYayandroidRecyclerViewActivity;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.recyclerview.banner.BannerLayout;

public class RecyclerBannerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BannerLayout recyclerBanner = findViewById(R.id.recycler);
        BannerLayout recyclerBannerVertical = findViewById(R.id.recycler_vertical);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(Constants.URL_IMG);
        }

        WebBannerAdapter webBannerAdapter = new WebBannerAdapter(this, list);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LToast.show(activity, "Click " + position);
            }
        });

        recyclerBanner.setAdapter(webBannerAdapter);
        recyclerBannerVertical.setAdapter(webBannerAdapter);
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
        return R.layout.activity_recyclerview_banner;
    }

}
