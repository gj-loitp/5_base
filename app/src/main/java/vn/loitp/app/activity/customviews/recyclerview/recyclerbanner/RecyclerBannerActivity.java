package vn.loitp.app.activity.customviews.recyclerview.recyclerbanner;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.LToast;
import vn.loitp.views.recyclerview.banner.BannerLayout;

public class RecyclerBannerActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NestedScrollView sv = (NestedScrollView) findViewById(R.id.sv);
        sv.setNestedScrollingEnabled(false);

        BannerLayout recyclerBanner = findViewById(R.id.recycler);
        BannerLayout recyclerBannerVertical = findViewById(R.id.recycler_vertical);
        BannerLayout rvBanner2 = findViewById(R.id.rv_banner_2);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(Constants.INSTANCE.getURL_IMG());
        }

        WebBannerAdapter webBannerAdapter = new WebBannerAdapter(this, list);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LToast.INSTANCE.show(activity, "Click " + position);
            }
        });

        recyclerBanner.setAdapter(webBannerAdapter);
        recyclerBannerVertical.setAdapter(webBannerAdapter);
        rvBanner2.setAdapter(webBannerAdapter);
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
