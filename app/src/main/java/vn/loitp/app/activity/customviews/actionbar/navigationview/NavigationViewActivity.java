package vn.loitp.app.activity.customviews.actionbar.navigationview;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.navigationview.LNavigationView;

public class NavigationViewActivity extends BaseFontActivity {
    private LNavigationView nv;
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nv = findViewById(R.id.nv);
        tvMsg = findViewById(R.id.tv_msg);

        nv.setColorOn(ContextCompat.getColor(activity, R.color.Red));
        nv.setColorOff(ContextCompat.getColor(activity, R.color.Gray));
        nv.getTv().setTextColor(Color.BLACK);
        LUIUtil.setTextSize(nv.getTv(), TypedValue.COMPLEX_UNIT_DIP, 20);//20dp

        final List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add("Item " + i);
        }

        nv.setStringList(stringList);
        nv.setNVCallback((index, s) -> {
            LLog.d(TAG, "onIndexChange " + index + " -> " + s);
            tvMsg.setText(index + " -> " + s);
        });
        findViewById(R.id.bt_0).setOnClickListener(view -> nv.setCurrenIndex(0));
        findViewById(R.id.bt_1).setOnClickListener(view -> nv.setCurrenIndex(stringList.size() - 1));
        findViewById(R.id.bt_2).setOnClickListener(view -> nv.setCurrenIndex(2));
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
        return R.layout.activity_navigation_view;
    }

}
