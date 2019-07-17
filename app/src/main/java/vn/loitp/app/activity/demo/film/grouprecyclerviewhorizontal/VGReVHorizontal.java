package vn.loitp.app.activity.demo.film.grouprecyclerviewhorizontal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.views.LToast;
import com.views.recyclerview.banner.BannerLayout;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class VGReVHorizontal extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();

    public interface Callback {
        void onClickRemove();
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

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

        final BannerLayout recyclerBanner = findViewById(R.id.recycler);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                list.add(Constants.INSTANCE.getURL_IMG_1());
            } else {
                list.add(Constants.INSTANCE.getURL_IMG_2());
            }
        }

        final VGReVAdapter vgReVAdapter = new VGReVAdapter(getContext(), list);
        vgReVAdapter.setOnBannerItemClickListener(position -> LToast.show(getContext(), "Click " + position));

        recyclerBanner.setAdapter(vgReVAdapter);

        findViewById(R.id.bt_remove).setOnClickListener(v -> {
            if (callback != null) {
                callback.onClickRemove();
            }
        });
    }
}