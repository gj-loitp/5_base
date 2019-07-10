package vn.loitp.app.activity.customviews.keyword_hottags;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.utils.util.ToastUtils;
import com.views.placeholderview.lib.placeholderview.PlaceHolderView;

import loitp.basemaster.R;

public class KeywordHotagsActivity extends BaseFontActivity {
    private PlaceHolderView placeHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeHolderView = (PlaceHolderView) findViewById(R.id.place_holder_view);
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
    protected int setLayoutResourceId() {
        return R.layout.activity_keyword_hottags;
    }
}
