package vn.loitp.app.activity.demo.film.grouprecyclerviewvertical;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.film.grouprecyclerviewhorizontal.VGReVAdapter;
import vn.loitp.app.common.Constants;
import vn.loitp.views.LToast;
import vn.loitp.views.recyclerview.banner.BannerLayout;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class VGReVVertical extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();

    public interface Callback {
        public void onClickRemove();
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public VGReVVertical(Context context) {
        super(context);
        findViews();
    }

    public VGReVVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        findViews();
    }

    public VGReVVertical(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        findViews();
    }

    private void findViews() {
        inflate(getContext(), R.layout.vg_rv_vertical, this);

        findViewById(R.id.bt_remove).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClickRemove();
                }
            }
        });
    }
}