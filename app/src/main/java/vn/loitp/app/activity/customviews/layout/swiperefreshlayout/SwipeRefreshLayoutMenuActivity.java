package vn.loitp.app.activity.customviews.layout.swiperefreshlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withplaceholderview.SwipeRefreshLayoutPlaceHolderViewActivity;
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withrecyclerview.SwipeRefreshLayoutRecyclerViewActivity;
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withscrollview.SwipeRefreshLayoutScrollViewActivity;

public class SwipeRefreshLayoutMenuActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_with_scroll_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SwipeRefreshLayoutScrollViewActivity.class);
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
            }
        });
        findViewById(R.id.bt_with_recycler_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SwipeRefreshLayoutRecyclerViewActivity.class);
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
            }
        });
        findViewById(R.id.bt_with_place_holder_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SwipeRefreshLayoutPlaceHolderViewActivity.class);
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
            }
        });
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
        return R.layout.activity_swipe_refresh_menu_layout;
    }
}
