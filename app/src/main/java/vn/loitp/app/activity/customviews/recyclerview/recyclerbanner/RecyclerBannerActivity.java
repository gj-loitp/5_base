package vn.loitp.app.activity.customviews.recyclerview.recyclerbanner;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;

import com.core.base.BaseFontActivity;
import com.views.LToast;
import com.views.recyclerview.banner.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.R;
import vn.loitp.app.common.Constants;

public class RecyclerBannerActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NestedScrollView sv = findViewById(R.id.sv);
        sv.setNestedScrollingEnabled(false);

        BannerLayout recyclerBanner = findViewById(R.id.recycler);
        BannerLayout recyclerBannerVertical = findViewById(R.id.recycler_vertical);
        BannerLayout rvBanner2 = findViewById(R.id.rv_banner_2);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(Constants.INSTANCE.getURL_IMG());
        }

        WebBannerAdapter webBannerAdapter = new WebBannerAdapter(this, list);
        webBannerAdapter.setOnBannerItemClickListener(position -> LToast.show(getActivity(), "Click " + position));

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
