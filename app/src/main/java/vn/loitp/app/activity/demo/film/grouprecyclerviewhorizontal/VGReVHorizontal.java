package vn.loitp.app.activity.demo.film.grouprecyclerviewhorizontal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.views.LToast;
import vn.loitp.views.recyclerview.banner.BannerLayout;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class VGReVHorizontal extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();

    public VGReVHorizontal(Context context) {
        super(context);
        findViews();
    }

    public VGReVHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
        findViews();
    }

    public VGReVHorizontal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        findViews();
    }

    private void findViews() {
        inflate(getContext(), R.layout.vg_rv_horizontal, this);

        BannerLayout recyclerBanner = findViewById(R.id.recycler);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                list.add(Constants.URL_IMG_1);
            } else {
                list.add(Constants.URL_IMG_2);
            }
        }

        VGReVAdapter vgReVAdapter = new VGReVAdapter(getContext(), list);
        vgReVAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LToast.show(getContext(), "Click " + position);
            }
        });

        recyclerBanner.setAdapter(vgReVAdapter);
    }
}