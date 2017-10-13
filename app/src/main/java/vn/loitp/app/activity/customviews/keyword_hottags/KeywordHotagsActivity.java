package vn.loitp.app.activity.customviews.keyword_hottags;

import android.app.Activity;
import android.os.Bundle;

import loitp.utils.util.ToastUtils;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.InfinitePlaceHolderView;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class KeywordHotagsActivity extends BaseActivity {
    private InfinitePlaceHolderView placeHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeHolderView = (InfinitePlaceHolderView) findViewById(R.id.place_holder_view);
        LUIUtil.setPullLikeIOSVertical(placeHolderView);

        int max = 50;
        String[] strings = new String[max];
        for (int i = 0; i < max; i++) {
            strings[i] = i + " loitp";
        }

        placeHolderView.addView(new PVKeywordList(activity, strings, new PVKeywordList.Callback() {
            @Override
            public void onClick(String keyword) {
                //LLog.d(TAG, "onClick " + keyword);
                ToastUtils.showShort("onClick " + keyword);
            }
        }));
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
        return R.layout.activity_keyword_hottags;
    }
}
