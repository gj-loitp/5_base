package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withscrollview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.core.base.BaseFontActivity;
import com.core.utilities.LStoreUtil;
import com.core.utilities.LUIUtil;
import com.views.LToast;

import vn.loitp.app.R;

public class SwipeRefreshLayoutScrollViewActivity extends BaseFontActivity {
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::doTask);
        LUIUtil.INSTANCE.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        TextView tv = findViewById(R.id.textView);
        String poem = LStoreUtil.Companion.readTxtFromRawFolder(getActivity(), R.raw.loitp);
        tv.setText(poem);
    }

    private void doTask() {
        LUIUtil.INSTANCE.setDelay(5000, () -> {
            swipeRefreshLayout.setRefreshing(false);
            LToast.show(activity, "Finish", R.drawable.l_bkg_horizontal);
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
