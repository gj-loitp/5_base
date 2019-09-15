package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withscrollview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.BaseFontActivity;
import com.core.utilities.LStoreUtil;
import com.core.utilities.LUIUtil;
import com.views.LToast;

import loitp.basemaster.R;

public class SwipeRefreshLayoutScrollViewActivity extends BaseFontActivity {
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doTask();
            }
        });
        LUIUtil.INSTANCE.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        TextView tv = (TextView) findViewById(R.id.tv);
        String poem = LStoreUtil.readTxtFromRawFolder(getActivity(), R.raw.loitp);
        tv.setText(poem);
    }

    private void doTask() {
        LUIUtil.INSTANCE.setDelay(5000, new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                LToast.show(activity, "Finish", R.drawable.l_bkg_horizontal);
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
        return R.layout.activity_swipe_refresh_scroll_view_layout;
    }
}
